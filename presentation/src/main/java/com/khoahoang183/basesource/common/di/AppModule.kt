package com.khoahoang183.basesource.common.di

import android.content.Context
import com.khoahoang183.basesource.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApplication(@ApplicationContext context: Context): Application {
        return context as Application
    }
}