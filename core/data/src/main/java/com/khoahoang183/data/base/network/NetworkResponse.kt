package com.khoahoang183.data.base.network

import retrofit2.HttpException
import retrofit2.Response


sealed class NetworkResponse<T> {

    data class ApiSuccessResponse<T>(val body: T, val errorBody: ErrorBody?= null) : NetworkResponse<T>()
    data class ApiUploadSuccessResponse<T>(val boolean: Boolean) : NetworkResponse<T>()

    data class ApiExceptionResponse<T>(val errorBody: ErrorBody, val httpCode: Int) :
        NetworkResponse<T>()

    companion object {
        private const val HTTP_UNSET = -1

        fun <T> createErrorResponse(throwable: Throwable): ApiExceptionResponse<T> {
            var httpCode: Int = HTTP_UNSET
            val errorBody = when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    httpCode = response?.code() ?: HTTP_UNSET
                    response?.errorBody()?.string()?.let {
                        ErrorBody.fromJson(it)
                    } ?: ErrorBody.empty
                }

                else -> ErrorBody.empty
            }
            return ApiExceptionResponse(errorBody, httpCode)
        }

        fun <T> createNetworkResponse(response: Response<T>): NetworkResponse<T> {
            return if (response.isSuccessful && response.body() != null || response.isSuccessful && response.code() == 204 ) {
                if(response.body() != null) {
                    ApiSuccessResponse(response.body()!!)
                } else {
                    ApiUploadSuccessResponse(true)
                }
            } else {
                val httpCode = response.code()
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    ErrorBody.fromMessage(response.message())
                } else {
                    ErrorBody.fromJson(msg)
                }
                ApiExceptionResponse(errorMsg, httpCode)
            }
        }
    }
}
