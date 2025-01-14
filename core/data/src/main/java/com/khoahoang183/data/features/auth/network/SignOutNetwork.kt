package com.khoahoang183.data.features.auth.network

import com.khoahoang183.data.base.network.BaseDataResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SignOutResponse : BaseDataResponse<Any>()