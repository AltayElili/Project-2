package com.example.project_2.di

import com.example.project_2.repository.AuthRepository
import com.example.project_2.repository.IAuthRepository
import com.example.project_2.repository.IProductRepository
import com.example.project_2.repository.ProductRepositoryImpl
import com.example.project_2.remoteDataSource.IAuthRemoteDataSource
import com.example.project_2.remoteDataSource.IProductRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun bindProductRepository(
        productRemoteDataSource: IProductRemoteDataSource
    ): IProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDataSource: IAuthRemoteDataSource): IAuthRepository {
        return AuthRepository(authRemoteDataSource)
    }
}