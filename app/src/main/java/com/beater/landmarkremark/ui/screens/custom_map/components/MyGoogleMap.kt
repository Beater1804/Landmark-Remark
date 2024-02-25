package com.beater.landmarkremark.ui.screens.custom_map.components

import android.graphics.Color
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Blue
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.helpers.LocationHelper
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState


@Composable
fun MyGoogleMap(
    allNote: List<Note>,
    latLngList: List<LatLng>,
    currentLocation: Location,
    cameraPositionState: CameraPositionState,
    onGpsIconClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(zoomControlsEnabled = false)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
    ) {
        Marker(
            state = MarkerState(position = LocationHelper.getPosition(currentLocation)),
            title = "Current Position",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

        latLngList.forEachIndexed() { index, latLng ->
            MarkerInfoWindow(
                state = MarkerState(position = latLng),
                title = allNote[index].title,
                snippet = if (allNote[index].userId == AppState.currentUser!!.uid)
                    "Description: ${allNote[index].description}"
                else "Author: ${allNote[index].email} \n Description: ${allNote[index].description}",
                icon = if (allNote[index].userId == AppState.currentUser!!.uid)
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
            ){ marker ->
                Column (
                    modifier = Modifier
                        .width(200.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Gray2)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
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
    }

    GpsIconButton(onIconClick = onGpsIconClick, onEditClick = onEditClick)
}