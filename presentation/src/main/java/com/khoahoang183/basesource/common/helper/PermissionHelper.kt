package com.khoahoang183.basesource.common.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


object PermissionHelper {

    fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun isGrantedResult(perms1: Array<out String>?, grantResults: IntArray?): Boolean {
        if (perms1 == null) return false
        if (grantResults == null) return false
        if (perms1.size != grantResults.size) return false
        perms1.forEachIndexed { index, s ->
            if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun isGrantedResult(grantResults: Map<String, Boolean>): Boolean {
        return grantResults.all { it.value }
    }

    fun requiresRationale(context: Activity, vararg perms1: String): Boolean {
        return perms1.any { ActivityCompat.shouldShowRequestPermissionRationale(context, it) }
    }
}