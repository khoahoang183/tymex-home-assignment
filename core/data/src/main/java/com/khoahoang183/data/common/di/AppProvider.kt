package com.khoahoang183.data.common.di

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

object AppProvider {
    fun getRetrofitBuilder(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
    }
}