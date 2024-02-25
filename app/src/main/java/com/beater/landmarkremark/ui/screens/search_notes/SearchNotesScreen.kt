package com.beater.landmarkremark.ui.screens.search_notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.beater.landmarkremark.models.ServiceError
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.ui.components.LRIconButton
import com.beater.landmarkremark.ui.components.LRLoading
import com.beater.landmarkremark.ui.components.LRSearchBox
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.screens.LRCommonScreen
import com.beater.landmarkremark.ui.screens.my_notes.components.NoteList
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.DEFAULT_SEARCH_KEYWORD
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.extensions.showToast
import com.beater.landmarkremark.view_models.SearchNotesViewModel

@Composable
fun SearchNotesScreen(
    navController: NavHostController,
    viewModel: SearchNotesViewModel = viewModel()
) {
    val context = LocalContext.current
    var keyword by rememberSaveable {
        mutableStateOf("")
    }

    val getMyNotesUiState = viewModel.getSearchNotesUiState.collectAsState().value

    val searchNotes = viewModel.searchNotes

    LaunchedEffect(Unit) {
        if(keyword.isNotEmpty()){
            viewModel.searchNote(keyword)
        } else {
            viewModel.searchNote(DEFAULT_SEARCH_KEYWORD)
        }
    }

    LRCommonScreen {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            LRText(
                text = stringResource(R.string.back),
                fontSize = 22.sp,
                fontWeight = LRFontWeight.Bold,
                color = BlackCustom,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .noRippleClickable {
                        keyword = ""

                        navController.navigate(NavRoute.MyNotes.route) {
                            popUpTo(0)
                        }
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LRSearchBox(
                    value = keyword,
                    onValueChange = { newValue ->
                        keyword = newValue
                    },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                LRIconButton(
                    icon = Icons.Filled.Search,
                    tint = BlackCustom,
                    iconSize = 20.dp,
                    modifier = Modifier
                        .weight(1f)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Gray2)
                ) {
                    if (keyword.isNotEmpty()) {
                        viewModel.searchNote(keyword)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (getMyNotesUiState) {
                    is UiState.Error<*> -> {
                        when (val error = getMyNotesUiState.errorType) {
                            is ServiceError.Specific -> context.showToast(stringResource(R.string.error))
                        }
                        viewModel.resetGetSearchNotesUiState()
                    }

                    UiState.Loading -> LRLoading()

                    is UiState.Success -> {
                        if (searchNotes.isNotEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                NoteList(list = searchNotes) { note ->
                                    navController.navigate(NavRoute.NoteDetail.withArgs(note))
                                }
                            }
                        } else {
                            Box {
                                if (keyword.isNotEmpty()) {
                                    LRText(text = stringResource(R.string.not_found_note))
                                } else {
                                    LRText(text = stringResource(R.string.please_search))
                                }

                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}