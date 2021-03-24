package com.shukhaev.rentateamtz.repository

import com.shukhaev.rentateamtz.data.NetworkResponse
import com.shukhaev.rentateamtz.db.UserDao
import com.shukhaev.rentateamtz.network.NetworkApi
import com.shukhaev.rentateamtz.network.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(private val api: NetworkApi, private val dao: UserDao) {

    suspend fun getUsersFromApi(): Resource<NetworkResponse> {
        return try {
            val response = api.getUsers()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                dao.insertUsers(result.users)
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            return Resource.Error("Check internet connection")
        } catch (e: HttpException) {
            return Resource.Error("Some server error")
        }
    }
}