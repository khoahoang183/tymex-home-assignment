package com.khoahoang183.data.base.network

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.khoahoang183.data.base.empty

data class ErrorBody(
    @SerializedName("message") var message: String? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("statusCode") var statusCode: String? = null,
    /*@SerializedName("statusCodeMsg") var statusCodeMsg: ArrayList<String> = arrayListOf(),
    @SerializedName("module") var module: String? = null,
    @SerializedName("method") var method: String? = null*/
    @SerializedName("codeName") var codeName: String? = null
) {
    companion object {
        val empty = ErrorBody(null, String.empty())

        fun fromMessage(message: String) = ErrorBody(null, message)

        fun fromJson(data: String): ErrorBody {
            return try {
                val json = GsonBuilder().serializeNulls().create()
                json.fromJson(data, ErrorBody::class.java)
            } catch (je: Exception) {
                empty
            }
        }
    }
}

data class ErrorBodyData(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message:ArrayList<String>?
) {
    companion object {
        val empty = ErrorBodyData(null, null)
    }
}