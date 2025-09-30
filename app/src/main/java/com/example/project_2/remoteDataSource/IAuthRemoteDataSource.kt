package com.example.project_2.remoteDataSource

import com.example.project_2.model.LoginResponseModel
import retrofit2.Response

interface IAuthRemoteDataSource {
    suspend fun loginUser(userName: String, password: String): Response<LoginResponseModel>
}