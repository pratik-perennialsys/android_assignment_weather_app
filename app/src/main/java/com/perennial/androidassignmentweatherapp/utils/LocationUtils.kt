package com.perennial.androidassignmentweatherapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.ArrayList

object LocationUtils {
    fun getLocationPermissionArray(): ArrayList<String> {
        val permissionsList = ArrayList<String>()
        permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionsList
    }

    fun checkLocationPermissions(context: Context): Boolean {
        for (permission in getLocationPermissionArray())
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(
                    context,
                    permission
                )
            ) return false
        return true
    }
}