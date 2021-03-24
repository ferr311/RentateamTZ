package com.shukhaev.rentateamtz.network

import com.shukhaev.rentateamtz.data.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {

    @GET("/users")
    suspend fun getUsers():Response<NetworkResponse>

}