package com.khoahoang183.data.common.network

import com.khoahoang183.data.base.network.BaseDataResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AnyResponse : BaseDataResponse<Any>()