package com.example.project_2

import android.net.http.HttpException
import com.example.project_2.service.NetworkResource
import retrofit2.Response

suspend fun <T> handleApi(
    execute: suspend () -> Response<T>
): NetworkResource<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResource.Success(body)
        } else {
            NetworkResource.Error(response.message())
        }
    } catch (e: HttpException) {
        NetworkResource.Error(e.message)
    } catch (e: Throwable) {
        NetworkResource.Error(e.message)
    }
}