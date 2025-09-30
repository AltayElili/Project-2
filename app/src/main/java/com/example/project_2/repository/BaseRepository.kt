package com.example.project_2.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.project_2.service.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun <T> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResource<T> {
        return try {
            withContext(Dispatchers.IO) {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    NetworkResource.Success(body)
                } else {
                    NetworkResource.Error(response.message())
                }
            }
        } catch (e: HttpException) {
            NetworkResource.Error(e.message ?:"")
        } catch (e: Throwable) {
            NetworkResource.Error(e.message?:"")
        }
    }
}
