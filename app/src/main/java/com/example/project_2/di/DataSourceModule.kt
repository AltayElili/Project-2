package com.example.project_2.di

import com.example.project_2.remoteDataSource.AuthRemoteDataSource
import com.example.project_2.remoteDataSource.IAuthRemoteDataSource
import com.example.project_2.remoteDataSource.IProductRemoteDataSource
import com.example.project_2.remoteDataSource.ProductRemoteDataSource
import com.example.project_2.service.AuthService
import com.example.project_2.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authService: AuthService): IAuthRemoteDataSource {
        return AuthRemoteDataSource(authService)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(productService: ProductService): IProductRemoteDataSource {
        return ProductRemoteDataSource(productService)
    }
}