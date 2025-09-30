package com.example.project_2

import android.content.Context
import com.example.project_2.di.AppModule
import com.example.project_2.di.DatabaseModule
import com.example.project_2.di.NetworkModule
import com.example.project_2.ui.HomeFragment
import com.example.project_2.ui.LoginFragment
import com.example.project_2.ui.ProductDetailsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class,AppModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ProductDetailsFragment)
    fun context(): Context
}