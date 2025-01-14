package com.khoahoang183.data.common.di

import com.khoahoang183.data.base.network.provider.JsonProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @MoshiUserAdapter
    @Provides
    fun provideUserMoshi(): Moshi = JsonProvider.moshiUserBuilder.build()
}