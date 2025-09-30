package com.example.project_2.service

import com.example.project_2.model.LoginResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: com.example.project_2.model.LoginRequest): Response<LoginResponseModel>

    @GET("auth/me")
    suspend fun getUserDetails(
    ): Response<LoginResponseModel>
}