package com.khoahoang183.basesource.common.extension


import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.khoahoang183.basesource.common.helper.PermissionHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import timber.log.Timber


fun Fragment.colorCompat(@ColorRes color: Int) =
    context?.colorCompat(color) ?: 0xFF000000.toInt()

fun Fragment.dpToPx(dp: Float): Int = requireContext().dpToPx(dp)

fun Fragment.getStringAndReset(key: String, defaultValue: String?): String? {
    if (arguments?.containsKey(key) == true) {
        val temp = arguments?.getString(key, defaultValue)
        arguments?.remove(key)
        return temp
    }
    return defaultValue
}
fun Fragment.executeAfter(delayDuration: Long, execute: () -> Unit) {
    this.lifecycleScope.launch {
        delay(delayDuration)
        ensureActive()
        execute.invoke()
    }
}

inline fun <reified T> Fragment.getParcelableAndReset(key: String): T? {
    if (arguments?.containsKey(key) == true) {
        val temp: T? = arguments?.parcelable(key)
        arguments?.remove(key)
        return temp
    }
    return null
}

fun Fragment.getBooleanAndReset(key: String, defaultValue: Boolean): Boolean {
    if (arguments?.containsKey(key) == true) {
        val temp = arguments?.getBoolean(key, defaultValue)
        arguments?.remove(key)
        return temp ?: defaultValue
    }
    return defaultValue
}

val Fragment.windowHeight: Int
    get() = requireActivity().windowHeight

val Fragment.windowWidth: Int
    get() = requireActivity().windowWidth

val Fragment.screenHeight: Int
    get() = requireActivity().screenHeight

val Fragment.screenWidth: Int
    get() = requireActivity().screenWidth

fun Fragment.hasGrantedPermissions(vararg permissions: String) =
    PermissionHelper.hasPermissions(requireContext(), *permissions)

fun Fragment.shouldRequestPermissionRationale(vararg permissions: String): Boolean {
    return PermissionHelper.requiresRationale(requireActivity(), *permissions)
}

fun Fragment.isResultPermissionGranted(grantResults: Map<String, Boolean>): Boolean {
    return PermissionHelper.isGrantedResult(grantResults)
}

fun Fragment.getColorByID(id : Int) : Int{
    return ContextCompat.getColor(requireContext(),id)
}


fun Fragment.checkPermission(
    permissionGranted: () -> Unit,
    permissionDenied: () -> Unit,
    permissionRequired: () -> Unit,
    permission: String
) {
    if (ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        permissionGranted.invoke()
        Timber.d("Anderson permissionGranted")
    } else if (shouldShowRequestPermissionRationale(permission)) {
        // In an educational UI, explain to the user why your app requires this
        // permission for a specific feature to behave as expected. In this UI,
        // include a "cancel" or "no thanks" button that allows the user to
        // continue using your app without granting the permission.
        permissionDenied.invoke()
        Timber.d("Anderson shouldShowRequestPermissionRationale")

    } else {
        permissionRequired.invoke()
        Timber.d("Anderson permissionRequired")
    }
}
