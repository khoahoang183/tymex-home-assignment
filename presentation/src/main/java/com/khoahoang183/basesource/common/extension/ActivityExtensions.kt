package com.khoahoang183.basesource.common.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.location.LocationManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.khoahoang183.basesource.common.helper.AppConstants
import timber.log.Timber


val Activity.windowHeight: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            var screenHeight = displayMetrics.heightPixels
            // find out if status bar has already been subtracted from screenHeight
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            val physicalHeight = displayMetrics.heightPixels
            val heightDelta = physicalHeight - screenHeight
            val statusBarHeight = statusBarHeightPx()
            val navigationBarHeight = navigationBarHeightPx()
            if (heightDelta == 0 || heightDelta == navigationBarHeight) {
                screenHeight -= statusBarHeight
            }
            screenHeight
        }
    }

val Activity.windowWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

val Activity.screenWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            metrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

val Activity.screenHeight: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            metrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

fun Activity.getVersionCodeName(): String {
    return try {
        val packageInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        "0.0.0"
    }
}


val Activity.readMediaImagesPermissions
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    else
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

val Activity.readMediaImagesPermissionsGranted
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
    else
        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


fun Activity.getArrayPermissionExt(permissionCategory: Int): Array<String> {
    var result = arrayOf("")
    when (permissionCategory) {
        AppConstants.EnumAppPermissionCategory.NOTIFICATION.value -> {
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else arrayOf("")
        }

        AppConstants.EnumAppPermissionCategory.STORAGE.value -> {
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            } else arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }

        AppConstants.EnumAppPermissionCategory.LOCATION.value -> {
            result = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        AppConstants.EnumAppPermissionCategory.CAMERA.value -> {
            result = arrayOf(
                Manifest.permission.CAMERA
            )
        }
    }
    return result
}


fun Activity.checkSelfPermissionExt(permissionCategory: Int): Boolean {
    when (permissionCategory) {
        AppConstants.EnumAppPermissionCategory.NOTIFICATION.value -> {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        }

        AppConstants.EnumAppPermissionCategory.STORAGE.value -> {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES,
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
        }

        AppConstants.EnumAppPermissionCategory.CAMERA.value -> {
            return (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED)
        }

        AppConstants.EnumAppPermissionCategory.LOCATION.value -> {

            val resultCoarse = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

            val resultFine = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

            /*
             if user grant precise -> both permission is granted
             if user grant approximate -> only ACCESS_COARSE_LOCATION is granted
             */

            return resultCoarse || resultFine
        }

        else -> {
            return true
        }
    }
}

fun Activity.checkAcceptAllPermissionExt(
    result: Map<String, Boolean>,
    callBackSuccess: (() -> Unit)? = null,
    callBackFail: (() -> Unit)? = null,
) {
    var isAcceptAll = true
    for (item in result.entries) {
        Timber.d("${this::class.java} - Permission ${item.key} = ${item.value}")
        if (!item.value) {
            isAcceptAll = false
        }
    }

    if (isAcceptAll)
        callBackSuccess?.invoke()
    else
        callBackFail?.invoke()
}

@SuppressLint("MissingPermission")
        /**
         * return a Set of <lat,long>
         * will return <null,null> if permission is not granted
         */
fun Activity.getCurrentLatLong(
    callBackLatLong: ((Double?, Double?) -> Unit)? = null
) {
    if (!checkSelfPermissionExt(AppConstants.EnumAppPermissionCategory.LOCATION.value)) {
        callBackLatLong?.invoke(null, null)
        return
    }
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    if (location != null) {
        val latitude = location.latitude
        val longitude = location.longitude
        Timber.d("Steve - latitude = $latitude")
        Timber.d("Steve - longitude = $longitude")
        callBackLatLong?.invoke(latitude, longitude)
    }
}

fun Activity.get(
    result: Map<String, Boolean>,
    callBackSuccess: (() -> Unit)? = null,
    callBackFail: (() -> Unit)? = null,
) {
    var isAcceptAll = true
    for (item in result.entries) {
        Timber.d("${this::class.java} - Permission ${item.key} = ${item.value}")
        if (!item.value) {
            isAcceptAll = false
        }
    }

    if (isAcceptAll)
        callBackSuccess?.invoke()
    else
        callBackFail?.invoke()
}

fun calculateJpegOrientation(rotation: Int, isFrontCamera: Boolean): Int {
    return if (isFrontCamera) {
        when (rotation) {
            Surface.ROTATION_0 -> 270
            Surface.ROTATION_90 -> 0
            Surface.ROTATION_180 -> 90
            Surface.ROTATION_270 -> 180
            else -> 270
        }
    } else {
        when (rotation) {
            Surface.ROTATION_0 -> 90
            Surface.ROTATION_90 -> 180
            Surface.ROTATION_180 -> 270
            Surface.ROTATION_270 -> 0
            else -> 90
        }
    }
}

private fun getDeviceOrientationOffset(rotation: Int, isFrontCamera: Boolean): Int {
    return if (isFrontCamera && rotation == Surface.ROTATION_0) {
        270
    } else {
        0
    }
}

fun Activity.getWindowManagerDefaultDisplay(): Display {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return windowManager.defaultDisplay
}

fun getSensorOrientation(cameraCharacteristics: CameraCharacteristics): Int {
    return cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
}

fun isFrontCamera(cameraCharacteristics: CameraCharacteristics): Boolean {
    val facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)
    return facing == CameraCharacteristics.LENS_FACING_FRONT
}