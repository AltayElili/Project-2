package com.example.project_2.di

import com.example.project_2.service.AuthService
import com.example.project_2.service.ProductService
import com.example.project_2.service.RefreshTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val BASE_URL = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp():OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(RefreshTokenInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun authService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun productService(retrofit:Retrofit) :ProductService{
        return retrofit.create(ProductService::class.java)
    }
}