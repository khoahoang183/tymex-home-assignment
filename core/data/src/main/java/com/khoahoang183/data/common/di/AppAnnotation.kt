package com.khoahoang183.data.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SecretInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StorageInfo


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitRefreshAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitUploadFile


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpNoAuth

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpUploadFile

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoshiConverter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GsonConverter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoshiNetworkAdapter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoshiUserAdapter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenInterceptor