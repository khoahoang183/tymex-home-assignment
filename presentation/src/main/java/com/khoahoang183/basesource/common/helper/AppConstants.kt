package com.khoahoang183.basesource.common.helper


import com.khoahoang183.basesource.BuildConfig


object AppConstants {

    const val TOAST_TIMER: Long = 4 * 1_000 // 3 seconds (+1s offset)
    const val DIALOG_SUCCESS_DURATION: Long = 600
    const val DIALOG_DUMMY_LOADING_DURATION: Long = 1000
    const val DELAY_EMIT_DURATION: Long = 10
    const val ANIMATION_DURATION: Long = 500L
    const val DAY_MILLI_SECONDS = 86400000L
    const val TAG_DIALOG = "TAG_DIALOG"


    val isDebugMode: Boolean = BuildConfig.DEBUG

    const val ENABLE_TEST_DATA: Boolean = true
    const val ENABLE_BYPASS_LOGIN: Boolean = false

    enum class EnumLoadingStatus(val value: String) {
        LOADING("LOADING"),
        DONE("DONE"),
        ANALYZE("ANALYZE"),
        SUCCESS("SUCCESS"),
    }

    enum class EnumAppPermissionCategory(val value: Int) {
        NOTIFICATION(10001),
        STORAGE(10002),
        LOCATION(10003),
        CAMERA(10004),
    }
}