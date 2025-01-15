package com.khoahoang183.data.common.di

import android.content.Context
import androidx.room.Room
import com.khoahoang183.data.base.database.AppDatabase
import com.khoahoang183.data.base.database.MIGRATION_1_2
import com.khoahoang183.data.common.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db_unreleased"
        ).addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}