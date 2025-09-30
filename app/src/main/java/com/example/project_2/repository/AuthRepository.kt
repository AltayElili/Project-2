package com.example.project_2.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.project_2.model.LoginResponseModel
import com.example.project_2.remoteDataSource.IAuthRemoteDataSource
import com.example.project_2.service.NetworkResource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: IAuthRemoteDataSource
): BaseRepository(), IAuthRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun signIn(username: String, password: String): NetworkResource<LoginResponseModel> {
        return handleApi {
            authRemoteDataSource.loginUser(username,password)
        }
    }
}
