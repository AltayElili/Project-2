package com.example.project_2.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("auth/refresh")
    fun refreshToken(@Body refreshToken: String): Call<TokenResponse>
}

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)