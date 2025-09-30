package com.example.project_2.di

import android.content.Context
import androidx.room.Room
import com.example.project_2.db.AppDatabase
import com.example.project_2.db.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context):AppDatabase{
        return  Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "project_2"
        ).fallbackToDestructiveMigration()
            .build()
    }
}