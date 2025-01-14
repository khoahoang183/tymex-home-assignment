package com.khoahoang183.data.features.auth.network

import com.khoahoang183.data.base.BaseParams

data class SignUpRequest(
    var username: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var password: String? = null,
    var address: String? = null,
    var otherAddress: String? = null,
    var userType: String? = null, // User - Trucker
) : BaseParams()