package com.khoahoang183.data.features.auth.network

import com.khoahoang183.data.base.network.BaseDataResponse
import com.khoahoang183.data.base.BaseParams
import com.khoahoang183.data.base.EnumAccountLevel
import com.khoahoang183.data.base.empty
import com.khoahoang183.data.base.orFalse
import com.khoahoang183.data.base.orMutableEmpty
import com.khoahoang183.data.base.orNoId
import com.khoahoang183.data.base.orZero
import com.khoahoang183.model.features.UserModel
import com.khoahoang183.model.features.UserModelTokens
import com.squareup.moshi.JsonClass

data class UserSignInModel(
    var user: UserModel,
    var tokens: UserModelTokens,
)

data class SignInRequest(
    val username: String,
    val password: String,
) : BaseParams()

@JsonClass(generateAdapter = true)
class SignInResponse : BaseDataResponse<UserSignInModel>()