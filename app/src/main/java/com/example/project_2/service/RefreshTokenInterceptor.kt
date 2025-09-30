package com.example.project_2.service

import android.util.Log
import com.example.project_2.service.RetrofitInstance
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request().newBuilder()
        request.header("Authorization", "Bearer ${RetrofitInstance.accessToken}")

        var response = chain.proceed(request.build())
        if (response.code == 401) {
            val newAccessToken = refreshAccessToken()

            if (newAccessToken.isNotEmpty()) {
                RetrofitInstance.accessToken = newAccessToken

                if (newAccessToken.isNotEmpty()) {
                    Log.d("MYTAG", "Access token tezelendi: $newAccessToken")
                    RetrofitInstance.accessToken = newAccessToken

                    val newRefreshToken = RetrofitInstance.refreshToken
                    Log.d("MYTAG", "Refresh token tezelendi: $newRefreshToken")

                    request = chain.request().newBuilder()
                    request.header("Authorization", "Bearer $newAccessToken")
                    response = chain.proceed(request.build())

                }
            }
        }
        return response
    }

    private fun refreshAccessToken(): String {

        val retrofit = RetrofitInstance.api
        val refreshTokenService = retrofit.create(RefreshTokenService::class.java)

        val call = refreshTokenService.refreshToken(RetrofitInstance.refreshToken)
        val response = call.execute()

        return if (response.isSuccessful && response.body() != null) {
            val newAccessToken = response.body()!!.accessToken
            Log.d("MYTAG","Teze Access Token alındı: $newAccessToken")
            newAccessToken
        } else {
            Log.d("MYTAG", "Access token tezelenmedi!")
            ""
        }
    }
}