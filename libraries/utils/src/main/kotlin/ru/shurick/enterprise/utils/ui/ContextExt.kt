package ru.shurick.enterprise.utils.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.getIdentifier(any: Any): Int {
    return this.resources.getIdentifier(any.toString(), "string", packageName)
}

fun startSettingsActivity(context: Context) {
    ContextCompat.startActivity(
        context,
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ),
        null
    )
}

fun Context.checkAllLocationPermissions(): Boolean {
    return checkCoarseLocationPermission() && checkFineLocationPermission() && checkBackgroundLocationPermission()
}

fun Context.checkCoarseLocationPermission(): Boolean =
    ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

fun Context.checkFineLocationPermission(): Boolean =
    ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

fun Context.checkBackgroundLocationPermission(): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    } else true