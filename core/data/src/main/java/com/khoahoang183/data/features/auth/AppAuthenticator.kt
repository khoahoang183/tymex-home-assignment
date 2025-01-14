package com.khoahoang183.data.features.auth

import com.khoahoang183.data.common.di.MoshiUserAdapter
import com.khoahoang183.data.common.shared.AuthSharedReferences
import com.khoahoang183.data.base.EnumUserStatus
import com.khoahoang183.data.base.EnumUserType
import com.khoahoang183.data.base.ifNotNullOrEmpty
import com.khoahoang183.data.features.auth.network.UserSignInModel
import com.khoahoang183.model.features.UserModel
import com.khoahoang183.model.features.UserModelTokens
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppAuthenticator @Inject constructor(
    @MoshiUserAdapter private val moshi: Moshi,
    private val authSharedReferences: AuthSharedReferences,
) {

    fun isLoggedIn(): Boolean {
        return authSharedReferences.accessToken.isNotBlank()
    }

    fun isActiveAccount(): Boolean {
        return getUserModel()?.userStatus == EnumUserStatus.ACTIVE.value ||
                getUserModel()?.status == EnumUserStatus.ACTIVE.value
    }

    val accessToken: String
        get() = authSharedReferences.accessToken

    val refreshToken: String
        get() = authSharedReferences.accessToken

    val userAuth: UserModel?
        get() = getUserModel()

    fun removeUserAuth() {
        authSharedReferences.clearTokens()
    }

    fun saveUserModelAuth(userSignInModel: UserSignInModel?) {
        userSignInModel?.let {
            it.tokens.accessToken.ifNotNullOrEmpty { authSharedReferences.accessToken = it }
            it.tokens.refreshToken.ifNotNullOrEmpty { authSharedReferences.refreshToken = it }
            saveUserModel(it.user)
        }
    }

    fun saveUserModelAuth(userModel: UserModel?) {
        if (userModel != null) {
            saveUserModel(userModel)
        }
    }

    fun updateUserModel(userModel: UserModel) {
        saveUserModel(userModel)
    }

    fun updateAccessToken(accessToken: String) {
        accessToken.ifNotNullOrEmpty { authSharedReferences.accessToken = it }
    }

    private fun saveUserModel(userModel: UserModel) {
        try {
            val adapter = moshi.adapter(UserModel::class.java)
            val json: String = adapter.toJson(userModel)
            authSharedReferences.userAuth = json
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
    }

    private fun getUserModel(): UserModel? {
        try {
            val adapter = moshi.adapter(UserModel::class.java)
            val json: String = authSharedReferences.userAuth
            if (json.length > 2) {
                return adapter.fromJson(json)
            }
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        return null
    }
}