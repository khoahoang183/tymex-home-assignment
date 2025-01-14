package com.khoahoang183.data.common.di

import com.khoahoang183.data.features.auth.network.AuthTokenResponse
import retrofit2.Call
import retrofit2.http.GET

interface RefreshTokenApiService {
    @GET("auth/access-token")
    fun getAccessToken(): Call<AuthTokenResponse>
}