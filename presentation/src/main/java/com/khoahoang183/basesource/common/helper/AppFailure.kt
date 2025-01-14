package com.khoahoang183.basesource.common.helper

import com.khoahoang183.data.base.Failure

sealed class AppFailure {

    object FeatureNotAvailableFailure : Failure.FeatureFailure()
}