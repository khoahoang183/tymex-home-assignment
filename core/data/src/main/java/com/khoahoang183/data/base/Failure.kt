package com.khoahoang183.data.base

import android.annotation.SuppressLint
import com.khoahoang183.data.base.network.ErrorBody
import com.khoahoang183.data.base.network.NetworkResponse
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


sealed class Failure : Throwable() {

    data object NetworkConnection : Failure() {
        private fun readResolve(): Any = NetworkConnection
    }

    data object TimeoutConnection : Failure() {
        private fun readResolve(): Any = TimeoutConnection
    }

    class UnknownFailure(val unknown: Throwable?) : Failure()

    class ServerError(val code: String?= null, val error: String?= null) : Failure()
    class UserError(val errorBody: ErrorBody?) : Failure()

    class CustomError(val code: String?, val error: String?) : Failure(){
        companion object{
            const val CUSTOM_ERROR_CODE_VALIDATE = "CUSTOM_ERROR_CODE_VALIDATE"
        }
    }

    /**
     * Extend this class for feature specific failures.
     */
    abstract class FeatureFailure : Failure()

    companion object {
        @SuppressLint("NewApi")
        fun Throwable.toFailure(): Failure {
            return when (this) {
                is HttpException -> {
                    val errorHttp = NetworkResponse.createErrorResponse<Nothing>(this)
                    when (errorHttp.httpCode) {
                        401, 403 -> UserFailure.SessionExpiredFailure
                        500, 502 -> {
                            ServerError(
                                code = errorHttp.httpCode.toString(),
                                error = "We apologize for any inconvenience caused in using the service.\nPlease try again later."
                            )
                        }

                        else -> {
                            if (errorHttp.errorBody.error != null) {
                                ServerError(
                                    code = errorHttp.errorBody.error,
                                    error = errorHttp.errorBody.error
                                )
                            } else {
                                ServerError(
                                    code = errorHttp.errorBody.error,
                                    error = errorHttp.errorBody.message
                                )
                            }

                        }
                    }
                }

                is SocketTimeoutException,
                is UnknownHostException,
                is TimeoutException,
                is InterruptedIOException -> NetworkConnection

                else -> UnknownFailure(this)
            }
        }

        fun<T> NetworkResponse.ApiExceptionResponse<T>.toFailure(): Failure {
            return when (this.httpCode) {
                401, 403 -> UserFailure.SessionExpiredFailure
                500, 502 -> {
                    ServerError(
                        code = this.httpCode.toString(),
                        error = "We apologize for any inconvenience caused in using the service.\nPlease try again later."
                    )
                }

                else -> {
                    if (this.errorBody.error != null) {
                        ServerError(
                            code = this.errorBody.error,
                            error = this.errorBody.error
                        )
                    } else {
                        ServerError(
                            code = this.errorBody.error,
                            error = this.errorBody.message
                        )
                    }
                }
            }
        }
    }
}