package com.example.project_2.repository

import com.example.project_2.model.LoginResponseModel
import com.example.project_2.service.NetworkResource

interface IAuthRepository {
    suspend fun signIn(username:String,password:String): NetworkResource<LoginResponseModel>
}