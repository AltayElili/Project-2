package com.example.project_2

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.project_2.model.LoginResponseModel
import com.example.project_2.repository.AuthRepository
import com.example.project_2.service.NetworkResource
import javax.inject.Inject

class AuthSignIn @Inject constructor(
    private val authRepository: AuthRepository
): IAuthSignIn {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun execute(userName: String, password: String): NetworkResource<LoginResponseModel> {
        return authRepository.signIn(userName, password)
    }
}