package com.khoahoang183.data.common.interceptor

import com.khoahoang183.data.common.di.RefreshTokenApiService
import com.khoahoang183.data.features.auth.AppAuthenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException
import javax.inject.Inject

class RequestAuthInterceptor @Inject constructor(
    private val appAuthenticator: AppAuthenticator,
    private val service: RefreshTokenApiService
) : okhttp3.Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            val newAccessToken = fetchAccessToken()
            if (newAccessToken != null) {
                appAuthenticator.updateAccessToken(newAccessToken)
                timber.log.Timber.tag("okhttp.OkHttpClient").d("Bearer '%s'", newAccessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .header("Authorization", newAccessToken)
                    .header("os-type", "android")
                    .build()
            } else null
        } catch (ignore: Exception) {
            null
        }
    }

    @Throws(IOException::class)
    private fun fetchAccessToken(): String? {
        var accessToken: String? = null
        val response = service.getAccessToken().execute()
        if (response.isSuccessful) {
            accessToken = response.body()?.data?.accessToken
        }
        return accessToken
    }
}