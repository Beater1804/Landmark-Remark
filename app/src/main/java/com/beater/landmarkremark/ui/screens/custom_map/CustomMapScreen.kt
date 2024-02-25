package com.beater.landmarkremark.ui.screens.custom_map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.ServiceError
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.models.ui_actions.AddNewNoteAction
import com.beater.landmarkremark.ui.components.LRLoading
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.components.dialogs.LRAddNewDialog
import com.beater.landmarkremark.ui.components.dialogs.LRLocationPermissionsAndSettingDialogs
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.screens.custom_map.components.MyGoogleMap
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.White
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.extensions.showToast
import com.beater.landmarkremark.utils.helpers.LocationHelper
import com.beater.landmarkremark.view_models.CustomMapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CustomMapScreen(
    navController: NavHostController,
    viewModel: CustomMapViewModel = viewModel()
) {
    val context = LocalContext.current
    var currentLocation by remember { mutableStateOf(LocationHelper.getDefaultLocation()) }

    val allNotes = viewModel.allNotes
    val allLatLng = viewModel.allLatlng
    val getAllNotesUiState = viewModel.getAllNotesUiState.collectAsState().value
    val addNoteUiState = viewModel.addNoteUiState.collectAsState().value

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LocationHelper.getPosition(currentLocation), 20f
    )

    var visibleAddNewNote by remember {
        mutableStateOf(false)
    }

    var requestLocationUpdate by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        LocationHelper.requestLocationResultCallback(AppState.fusedLocationProviderClient!!) { locationResult ->

            locationResult.lastLocation?.let { location ->
                currentLocation = location
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllNotes()
    }


    Box {

        when (getAllNotesUiState) {
            is UiState.Error<*> -> {
                when (val error = getAllNotesUiState.errorType) {
                    is ServiceError.Specific -> context.showToast(stringResource(R.string.error))
                }
                viewModel.resetGetAllNotesUiState()
            }

            UiState.Loading -> LRLoading()

            is UiState.Success -> {
                MyGoogleMap(
                    allNotes,
                    allLatLng,
                    currentLocation,
                    cameraPositionState,
                    onGpsIconClick = {
                        requestLocationUpdate = true
                    },
                    onEditClick = {
                        visibleAddNewNote = true
                    }
                )
            }

            else -> {}
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
                    navController.navigate(NavRoute.MyNotes.route) {
                        popUpTo(0)
                    }
                }
        )
    }

    if (requestLocationUpdate) {
        LRLocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                LocationHelper.requestLocationResultCallback(AppState.fusedLocationProviderClient!!) { locationResult ->

                    locationResult.lastLocation?.let { location ->
                        currentLocation = location
                    }

                }
            }
        )
    }

    if (visibleAddNewNote) {
        LRAddNewDialog { action ->
            when (action) {
                AddNewNoteAction.OnClose -> visibleAddNewNote = false

                is AddNewNoteAction.OnSave -> {
                    viewModel.addNote(
                        title = action.title,
                        description = action.description,
                        location = currentLocation
                    )
                    visibleAddNewNote = false
                }
            }
        }
    }

    when (addNoteUiState) {
        is UiState.Error<*> -> {
            when (val error = addNoteUiState.errorType) {
                is ServiceError.Specific -> context.showToast(stringResource(R.string.error))
            }
            viewModel.resetAddNoteUiState()
        }

        UiState.Loading -> LRLoading()

        is UiState.Success -> {
            context.showToast(stringResource(R.string.add_note_successful))
            viewModel.resetAddNoteUiState()

            LaunchedEffect(Unit) {
                viewModel.getAllNotes()
            }
        }

        else -> {}
    }
}

