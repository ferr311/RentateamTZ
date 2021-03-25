package com.shukhaev.rentateamtz.repository

import android.content.Context
import com.shukhaev.rentateamtz.R
import com.shukhaev.rentateamtz.data.NetworkResponse
import com.shukhaev.rentateamtz.db.UserDao
import com.shukhaev.rentateamtz.network.NetworkApi
import com.shukhaev.rentateamtz.network.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: NetworkApi,
    private val dao: UserDao,
    @ApplicationContext private val context: Context
) {

    suspend fun getUsersFromApi(): Resource<NetworkResponse> {
        return try {
            val response = api.getUsers()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                dao.insertUsers(result.users)
                Resource.Success(result)
            } else {
                Resource.Error(context.getString(R.string.server_error))
            }
        } catch (e: IOException) {
            return Resource.Error(context.getString(R.string.io_error))
        } catch (e: HttpException) {
            return Resource.Error(context.getString(R.string.http_error))
        } catch (e: Exception) {
            return Resource.Error(context.getString(R.string.unepected_error))
        }
    }

    fun getUsersFromDb() = dao.getAllUsers()
}