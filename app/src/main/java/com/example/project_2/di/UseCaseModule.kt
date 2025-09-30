package com.example.project_2.di

import com.example.project_2.AuthSignIn
import com.example.project_2.FetchProductUseCase
import com.example.project_2.repository.IAuthRepository
import com.example.project_2.IAuthSignIn
import com.example.project_2.IFetchProductUseCase
import com.example.project_2.repository.AuthRepository
import com.example.project_2.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthSignInUseCase(authRepository: IAuthRepository): IAuthSignIn {
        return AuthSignIn(authRepository as AuthRepository)
    }

    @Provides
    @Singleton
    fun provideFetchProductUseCase(productRepository: ProductRepositoryImpl): IFetchProductUseCase {
        return FetchProductUseCase(productRepository)
    }
}