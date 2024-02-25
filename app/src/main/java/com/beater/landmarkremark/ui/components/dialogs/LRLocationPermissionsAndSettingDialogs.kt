package com.beater.landmarkremark.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.beater.landmarkremark.utils.helpers.LocationHelper

@Composable
fun LRLocationPermissionsAndSettingDialogs(
    updateCurrentLocation: () -> Unit,
) {
    var requestLocationSetting by remember { mutableStateOf(false) }

    if (LocationHelper.isLocationPermissionGranted(LocalContext.current)) {
        SideEffect {
            requestLocationSetting = true
        }
    } else {
        LRLocationPermissionsDialog(
            onPermissionGranted = {
                requestLocationSetting = true
            },
            onPermissionDenied = {
                requestLocationSetting = true
            },
        )
    }

    if (requestLocationSetting) {
        LRLocationSettingDialog(
            onSuccess = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
            onFailure = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
        )
    }
}