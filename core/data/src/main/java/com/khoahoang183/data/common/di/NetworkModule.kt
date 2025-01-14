package com.khoahoang183.data.common.di

import com.google.gson.Gson
import com.khoahoang183.data.common.interceptor.AppAuthInterceptor
import com.khoahoang183.data.common.interceptor.RequestAuthInterceptor
import com.khoahoang183.data.common.`object`.ConverterFactoryObject
import com.khoahoang183.data.common.`object`.JsonBuilderObject
import com.khoahoang183.data.common.`object`.OkHttpClientObject
import com.khoahoang183.data.common.`object`.Secrets
import com.khoahoang183.data.features.auth.AppAuthenticator
import com.khoahoang183.data.features.auth.AuthApiService
import com.khoahoang183.data.features.file.FileApiService
import com.khoahoang183.data.features.user.UserApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @EndpointInfo
    @Provides
    fun provideBaseApiUrl() = Secrets.apiEndpointUrl

    @MoshiConverter
    @Provides
    fun provideConverterFactoryMoshi(@MoshiNetworkAdapter moshi: Moshi): Converter.Factory =
        ConverterFactoryObject.getConverterFactory(moshi)

    @MoshiNetworkAdapter
    @Provides
    fun provideNetworkMoshi(): Moshi = JsonBuilderObject.moshiNetworkBuilder.build()

    @GsonConverter
    @Provides
    fun provideConverterFactoryGson(gson: Gson): Converter.Factory =
        ConverterFactoryObject.getConverterFactory(gson)

    @LoggingInterceptor
    @Provides
    fun providerLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @TokenInterceptor
    @Provides
    fun providerHeaderInterceptor(authenticator: AppAuthenticator): Interceptor {
        return AppAuthInterceptor(authenticator)
    }

    @OkHttpUploadFile
    @Provides
    fun provideUploadOkHttpClient(
        authenticator: okhttp3.Authenticator,
        @LoggingInterceptor logging: Interceptor,
        @TokenInterceptor header: Interceptor
    ): OkHttpClient =
        OkHttpClientObject.getOkHttpClientBuilder(logging, header, authenticator).build()

    @Provides
    fun providerRefreshAuthInterceptor(
        authenticator: AppAuthenticator,
        serviceApi: RefreshTokenApiService
    ): okhttp3.Authenticator = RequestAuthInterceptor(authenticator, serviceApi)

    @OkHttpAuth
    @Provides
    fun provideOkHttpClient(
        authenticator: okhttp3.Authenticator,
        @LoggingInterceptor logging: Interceptor,
        @TokenInterceptor header: Interceptor
    ): OkHttpClient =
        OkHttpClientObject.getOkHttpClientBuilder(logging, header, authenticator).build()

    @OkHttpNoAuth
    @Provides
    fun provideOkHttpClientNoAuth(
        @LoggingInterceptor logging: Interceptor,
    ): OkHttpClient = OkHttpClientObject.getOkHttpClientBuilder(logging).build()

    @RetrofitAuth
    @Provides
    fun provideRetrofitAuth(
        @EndpointInfo baseUrl: String,
        @OkHttpAuth okHttpClient: OkHttpClient,
        @MoshiConverter converterFactory2: Converter.Factory,
    ): Retrofit = AppProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory2)
        .build()

    @RetrofitRefreshAuth
    @Provides
    fun provideRetrofitRefreshAuth(
        @EndpointInfo baseUrl: String,
        @OkHttpNoAuth okHttpClient: OkHttpClient,
        @MoshiConverter converterFactory2: Converter.Factory,
    ): Retrofit = AppProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory2)
        .build()


    @RetrofitUploadFile
    @Provides
    fun provideRetrofitUploadFile(
        @EndpointInfo baseUrl: String,
        @OkHttpUploadFile okHttpClient: OkHttpClient,
        @MoshiConverter converterFactory: Converter.Factory,
    ): Retrofit = AppProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideRefreshTokenApiService(@RetrofitRefreshAuth retrofit: Retrofit): RefreshTokenApiService {
        return retrofit.create(RefreshTokenApiService::class.java)
    }

    @Provides
    fun provideAuthApiService(@RetrofitAuth retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideUserApiService(@RetrofitAuth retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    fun provideFileApiService(@RetrofitUploadFile retrofit: Retrofit): FileApiService {
        return retrofit.create(FileApiService::class.java)
    }
}