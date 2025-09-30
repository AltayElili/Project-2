package com.example.project_2

import com.example.project_2.model.LoginResponseModel
import com.example.project_2.service.NetworkResource
import dagger.Provides

@Provides
interface IAuthSignIn {
    suspend fun execute(userName: String, password: String): NetworkResource<LoginResponseModel>
}
