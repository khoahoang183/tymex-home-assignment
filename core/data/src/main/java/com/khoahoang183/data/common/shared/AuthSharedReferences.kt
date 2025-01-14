package com.khoahoang183.data.common.shared

import android.content.Context
import com.khoahoang183.data.base.shared.BaseSharedPreferences
import com.khoahoang183.data.common.di.SecretInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
class AuthSharedReferences(
    @ApplicationContext applicationContext: Context,
    @SecretInfo secretName: String
) : BaseSharedPreferences() {

    companion object {
        const val PREFS_USER_ACCESS_TOKEN = "user_access_token"
        const val PREFS_USER_REFRESH_TOKEN = "user_refresh_token"
        const val PREFS_USER_OWNER = "user_owner"
    }

    init {
        sharedPreferences = applicationContext.getSharedPreferences(
            "${secretName}_${VERSION}",
            Context.MODE_PRIVATE
        )
    }

    fun clearTokens() {
        this.accessToken = ""
        this.refreshToken = ""
    }

    var accessToken: String
        get() = this[PREFS_USER_ACCESS_TOKEN, ""]
        set(value) {
            this[PREFS_USER_ACCESS_TOKEN] = value
        }

    var refreshToken: String
        get() = this[PREFS_USER_REFRESH_TOKEN, ""]
        set(value) {
            this[PREFS_USER_REFRESH_TOKEN] = value
        }

    var userAuth: String
        get() = this[PREFS_USER_OWNER, ""]
        set(value) {
            this[PREFS_USER_OWNER] = value
        }

}