package com.example.project_2.remoteDataSource

import com.example.project_2.model.LoginResponseModel
import com.example.project_2.model.LoginRequest
import com.example.project_2.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
): IAuthRemoteDataSource {
    override suspend fun loginUser(userName: String, password: String):Response<LoginResponseModel> {
        val response = authService.loginUser(
            LoginRequest(userName,password)
        )
        return response
    }
}