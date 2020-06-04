package com.app.core.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat

data class PermissionData(val requestCode: Int, val permission: Array<String>, val grantResult: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PermissionData

        if (requestCode != other.requestCode) return false
        if (!permission.contentEquals(other.permission)) return false
        if (!grantResult.contentEquals(other.grantResult)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = requestCode
        result = 31 * result + permission.contentHashCode()
        result = 31 * result + grantResult.contentHashCode()
        return result
    }
}

data class RequestResultData(val requestCode: Int, val resultCode: Int, val data: Intent?)

internal interface BasePermission {

    fun isGranted(permission: Int): Boolean {
        return permission == PERMISSION_GRANTED
    }

    fun isLocationGranted(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    }
}
