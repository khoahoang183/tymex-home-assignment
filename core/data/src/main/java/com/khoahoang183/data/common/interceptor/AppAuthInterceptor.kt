package com.khoahoang183.data.common.interceptor

import com.khoahoang183.data.features.auth.AppAuthenticator
import okhttp3.Request
import timber.log.Timber

class AppAuthInterceptor(private val authenticator: AppAuthenticator) : NetworkInterceptor() {

    private val accessToken: String
        get() = authenticator.accessToken

    override fun applyOptions(builder: Request.Builder) {
        builder.addHeader("Content-Type", "application/json")
        val authorization = accessToken
        if (authorization.isNotBlank()) {
            builder.addHeader("Authorization", "Bearer $authorization")
            Timber.tag("okhttp.OkHttpClient").d("Bearer '%s'", authorization)
        }
    }
}