package com.example.project_2.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"
    var accessToken: String = ""
    var refreshToken: String = ""

    val api: Retrofit by lazy {
        val okhttp3 = OkHttpClient.Builder()
            .addInterceptor(RefreshTokenInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}