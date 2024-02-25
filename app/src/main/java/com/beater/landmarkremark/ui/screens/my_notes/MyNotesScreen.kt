package com.beater.landmarkremark.ui.screens.my_notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.beater.landmarkremark.models.ServiceError
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.ui.components.LRIconButton
import com.beater.landmarkremark.ui.components.LRLoading
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.screens.LRCommonScreen
import com.beater.landmarkremark.ui.screens.my_notes.components.NoteList
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.White
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.extensions.showToast
import com.beater.landmarkremark.view_models.MyNotesViewModel

@Composable
fun MyNotesScreen(
    navController: NavHostController,
    viewModel: MyNotesViewModel = viewModel()
) {
    val context = LocalContext.current

    val getMyNotesUiState = viewModel.getMyNotesUiState.collectAsState().value

    val notes = viewModel.notes

    LaunchedEffect(Unit) {
        viewModel.getMyNotes(AppState.currentUser!!.uid)
    }

    LRCommonScreen {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LRText(
                        text = "Welcome, ${AppState.currentUser?.email ?: ""}",
                        fontSize = 20.sp,
                        fontWeight = LRFontWeight.Bold,
                        color = BlackCustom
                    )

                    LRIconButton(
                        icon = Icons.Filled.Logout,
                        tint = BlackCustom,
                        iconSize = 20.dp,
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        viewModel.signOut()

                        navController.navigate(NavRoute.FlowSelection.route) {
                            popUpTo(0)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LRText(
                    text = stringResource(R.string.list_of_short_notes),
                    fontSize = 20.sp,
                    fontWeight = LRFontWeight.Bold,
                    color = BlackCustom
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (getMyNotesUiState) {
                        is UiState.Error<*> -> {
                            when (val error = getMyNotesUiState.errorType) {
                                is ServiceError.Specific -> context.showToast(stringResource(R.string.error))
                            }
                            viewModel.resetGetMyNotesUiState()
                        }

                        UiState.Loading -> LRLoading()

                        is UiState.Success -> {
                            if (notes.isNotEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    NoteList(list = notes) { note ->
                                        navController.navigate(NavRoute.NoteDetail.withArgs(note))
                                    }
                                }
                            } else {
                                LRText(text = stringResource(R.string.empty_note))
                            }
                        }

                        else -> {}
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 36.dp)
                    .height(100.dp)
                    .align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LRIconButton(
                    icon = Icons.Filled.Search,
                    tint = White,
                    iconSize = 20.dp,
                    modifier = Modifier
                        .weight(1f)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Gray2)
                ) {
                    navController.navigate(NavRoute.SearchNotes.route)
                }

                LRIconButton(
                    icon = Icons.Filled.Map,
                    tint = White,
                    iconSize = 20.dp,
                    modifier = Modifier
                        .weight(1f)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Gray2)
                ) {
                    navController.navigate(NavRoute.CustomMap.route)
                }
            }
        }
    }
}