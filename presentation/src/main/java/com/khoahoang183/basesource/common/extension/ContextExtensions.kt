package com.khoahoang183.basesource.common.extension

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Context.statusBarHeightPx(): Int {
    var height = 0
    try {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
    } catch (ignore: Exception) {}
    return height
}

fun Context.actionBarHeightPx(): Int {
    var height = 0
    try {
        theme.obtainStyledAttributes(
            intArrayOf(R.attr.actionBarSize)
        ).use {
            height = it.getDimension(0, 0f).toInt()
        }
    } catch (ignore: Exception) {}
    return height
}

@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun Context.navigationBarHeightPx(): Int {
    var height = 0
    try {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
    } catch (ignore: Exception) {}
    return height
}

fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Context.dpToPxFloat(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.colorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.colorListCompat(@ColorRes color: Int) = ColorStateList.valueOf(colorCompat(color))

fun Context.drawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

