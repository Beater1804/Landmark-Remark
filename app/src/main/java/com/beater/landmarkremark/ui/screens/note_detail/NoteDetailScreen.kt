package com.beater.landmarkremark.ui.screens.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.components.dialogs.LRLocationPermissionsAndSettingDialogs
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.White
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.helpers.LocationHelper
import com.beater.landmarkremark.view_models.NoteDetailViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun NoteDetailScreen(
    navController: NavHostController,
    note: Note,
    viewModel: NoteDetailViewModel = viewModel()
) {
    val currentLatLng = LatLng(note.lat, note.long)

    var requestLocationUpdate by remember { mutableStateOf(true) }

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        currentLatLng, 20f
    )

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(zoomControlsEnabled = false)
        )
    }

    LaunchedEffect(Unit) {
        LocationHelper.requestLocationResultCallback(AppState.fusedLocationProviderClient!!) {
            //TODO: Not handle
        }
    }

    Box(modifier = Modifier) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings,
        ) {
            MarkerInfoWindow(
                state = MarkerState(position = currentLatLng),
                title = note.title,
                snippet = "Author: ${note.email} \n Description: ${note.description}",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)
            ) { marker ->
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Gray2)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LRText(
                        text = marker.title.toString(),
                        fontSize = 18.sp,
                        fontWeight = LRFontWeight.Bold,
                        color = BlackCustom
                    )
                    LRText(text = marker.snippet.toString())
                }
            }
        }

        LRText(
            text = stringResource(R.string.back),
            fontSize = 22.sp,
            fontWeight = LRFontWeight.Bold,
            color = BlackCustom,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
                .background(White)
                .noRippleClickable {
                    navController.popBackStack()
                }
        )
    }

    if (requestLocationUpdate) {
        LRLocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                LocationHelper.requestLocationResultCallback(AppState.fusedLocationProviderClient!!) {
                    //TODO: Not handle
                }
            }
        )
    }
}