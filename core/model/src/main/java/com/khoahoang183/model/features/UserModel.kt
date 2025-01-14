package com.khoahoang183.model.features

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String? = null,
    val userId: String? = null,
    var name: String? = null,
    var userName: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var userType: String? = null, // Influencer, Business
    var userStatus: String? = null,
    var status: String? = null,
    var avatar: String? = null,
    var description: String? = null,
    var countryName: String? = null,
    var dob: String? = null,
    var rating: Float? = null,

    var tokens: UserModelTokens? = null,

) : Parcelable

@Parcelize
data class UserModelTokens(
    val accessToken: String,
    var refreshToken: String,
) : Parcelable

