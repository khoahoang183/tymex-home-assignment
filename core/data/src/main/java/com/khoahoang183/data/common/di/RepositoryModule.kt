package com.khoahoang183.data.common.di

import com.khoahoang183.data.common.dao.UserDao
import com.khoahoang183.data.features.auth.AppAuthenticator
import com.khoahoang183.data.features.auth.AuthApiService
import com.khoahoang183.data.features.auth.repository.AuthRepository
import com.khoahoang183.data.features.auth.repository.AuthRepositoryImpl
import com.khoahoang183.data.features.file.FileApiService
import com.khoahoang183.data.features.file.repository.FileRepository
import com.khoahoang183.data.features.file.repository.FileRepositoryImpl
import com.khoahoang183.data.features.user.UserApiService
import com.khoahoang183.data.features.user.repository.UserRepository
import com.khoahoang183.data.features.user.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(
        appAuthenticator: AppAuthenticator,
        apiService: AuthApiService
    ): AuthRepository {
        return AuthRepositoryImpl(appAuthenticator, apiService)
    }

    @Provides
    fun provideUserRepository(
        appAuthenticator: AppAuthenticator,
        apiService: UserApiService,
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(appAuthenticator, apiService, userDao)
    }

    @Provides
    fun provideFileRepository(apiService: FileApiService): FileRepository {
        return FileRepositoryImpl(apiService)
    }

    /*@Provides
    fun provideOtherRepository(
        appAuthenticator: AppAuthenticator,
        apiService: OtherApiService
    ): OtherRepository {
        return OtherRepositoryImpl(appAuthenticator, apiService)
    }*/
}