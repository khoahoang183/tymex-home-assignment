package com.khoahoang183.data.common.di

import android.content.Context
import com.khoahoang183.data.common.shared.AuthSharedReferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @StorageInfo
    @Provides
    fun provideStorageName() = "app_storage_shared_prefs"

    @SecretInfo
    @Provides
    fun provideSecretName() = "app_secret_shared_prefs"

    @Singleton
    @Provides
    fun provideAuthSharedReferences(
        @ApplicationContext context: Context,
        @SecretInfo secretName: String
    ) = AuthSharedReferences(context, secretName)
}