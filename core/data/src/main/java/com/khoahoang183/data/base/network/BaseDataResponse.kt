package com.khoahoang183.data.base.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
open class BaseDataResponse<T>(
//    @property:Json(name = "statusCode") var statusCode: Int? = null,
//    @property:Json(name = "status") var status: String? = null,
//    @property:Json(name = "message") var message: String? = null,
//    @property:Json(name = "data") var data: T? = null

    @property:Json(name = "statusCode") var statusCode: Int? = null,
    @property:Json(name = "status") var status: Boolean? = null,
    @property:Json(name = "message") var message: String? = null,
    @property:Json(name = "data") var data: T? = null,
    @property:Json(name = "error") var error: ErrorBody? = null
)

@JsonClass(generateAdapter = true)
open class DataMessage(
    @Json(name = "code") var code: Int? = null,
    @Json(name = "text") var text: String? = null
)