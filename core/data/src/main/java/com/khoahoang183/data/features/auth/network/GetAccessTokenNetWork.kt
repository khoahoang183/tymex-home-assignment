package com.khoahoang183.data.features.auth.network

import com.khoahoang183.data.base.network.BaseDataResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AuthTokenResponse : BaseDataResponse<AuthTokenData>()

@JsonClass(generateAdapter = true)
data class AuthTokenData(
    @Json(name = "accessToken") val accessToken: String?
)