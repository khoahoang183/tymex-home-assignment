package com.khoahoang183.data.common.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

open class NetworkInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
        // Do custom intercepting activities here, for example: appending accessToken header if required
        applyOptions(newRequest)

        val afterIntercepted = newRequest.build()
        return chain.proceed(afterIntercepted)
    }

    open fun applyOptions(builder: Request.Builder) {
        // Default empty impl.
    }
}