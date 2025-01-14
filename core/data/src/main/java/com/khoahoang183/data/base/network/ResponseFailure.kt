package com.khoahoang183.data.base.network

import com.khoahoang183.data.base.Failure

class ResponseFailure {
    object SuccessEmptyResponse : Failure.FeatureFailure()
}