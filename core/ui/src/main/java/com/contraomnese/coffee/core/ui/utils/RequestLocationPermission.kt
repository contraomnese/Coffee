package com.contraomnese.coffee.core.ui.utils

import android.Manifest
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(onGranted: () -> Unit) {
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val permissionState = rememberPermissionState(locationPermission)

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            onGranted()
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            onGranted()
        }
        is PermissionStatus.Denied -> Unit
    }
}