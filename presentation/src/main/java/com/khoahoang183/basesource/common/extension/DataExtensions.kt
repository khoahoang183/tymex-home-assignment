@file:Suppress("DEPRECATION")

package com.khoahoang183.basesource.common.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.basesource.common.helper.AppFailure
import com.khoahoang183.data.base.Failure
import com.khoahoang183.data.base.UserFailure
import java.lang.reflect.Field


fun Any?.notNull(): Boolean = this != null

fun Failure.toErrorString(): String? {
    val defError = "Something went wrong! Please try again"
    return when (this) {
        is AppFailure.FeatureNotAvailableFailure -> "Oops, this feature isn\'t available right now!"
        is UserFailure.UserBlockedPermissionFailure -> "You can\'t create contents because your account is blocked by admin."
        is Failure.NetworkConnection -> "The network connection is lost"
        is Failure.TimeoutConnection -> "Unable to connect to the Internet"
        is Failure.UnknownFailure -> {
            if (AppConstants.isDebugMode) this.unknown?.localizedMessage ?: defError
            else defError
        }
        is Failure.ServerError -> if (!this.error.isNullOrEmpty()) this.error else defError
        else -> defError
    }
}

