package com.khoahoang183.data.features.auth

import com.khoahoang183.data.common.network.AnyResponse
import com.khoahoang183.data.features.auth.network.SignInRequest
import com.khoahoang183.data.features.auth.network.SignUpRequest
import com.khoahoang183.data.features.auth.network.UserSignInModel
import com.khoahoang183.model.features.UserModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {
    //@POST("auth/sign-in/business")
    @POST("auth/sign-in")
    fun signIn(
        @Body body: SignInRequest
    ): Call<UserSignInModel>

    @POST("auth/sign-out")
    fun signOut(): Call<Any>


    @POST("auth/sign-up")
    fun signUp(@Body body: SignUpRequest): Call<AnyResponse>

    @GET("auth/profile")
    fun getMyProfile(): Call<UserModel>

}