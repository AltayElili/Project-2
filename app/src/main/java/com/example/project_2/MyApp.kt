package com.example.project_2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application()    {
lateinit var appComponent: ApplicationComponent
override fun onCreate() {
    super.onCreate()
    appComponent = DaggerApplicationComponent.builder()
        .appModule(AppModule(this))
        .build()
}
}