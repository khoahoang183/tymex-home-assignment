package com.khoahoang183.data.features.user

import com.khoahoang183.model.features.GithubUserModel
import com.khoahoang183.model.features.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("users")
    fun getGithubUsers(
        @Query("per_page") per_page: Int,
        @Query("since") since: Int
    ): Call<List<GithubUserModel>>

    @GET("users/{userId}")
    fun getGithubUserDetail(
        @Path("userId") userId: String,
    ): Call<GithubUserModel>
}