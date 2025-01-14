package com.khoahoang183.data.base

sealed class UserFailure {

    class UserSnsAuthFailure(val error: String?) : Failure.FeatureFailure()

    object UserBlockedPermissionFailure : Failure.FeatureFailure()

    object SessionExpiredFailure : Failure.FeatureFailure()
}