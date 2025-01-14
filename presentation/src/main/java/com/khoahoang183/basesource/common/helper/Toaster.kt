package com.khoahoang183.basesource.common.helper

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class Toaster @Inject constructor(@ApplicationContext private val context: Context) {

    private var toast: Toast? = null

    open fun show(@StringRes messageRes: Int) {
        show(context.getString(messageRes))
    }

    open fun show(message: CharSequence?) {
        message ?: return
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG).also { it.show() }
    }
}