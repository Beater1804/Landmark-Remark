package com.beater.landmarkremark.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.extensions.popUpToTop
import com.beater.landmarkremark.view_models.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        LRText(
            text = stringResource(id = R.string.app_name),
            fontWeight = LRFontWeight.Black,
            fontSize = 30.sp
        )
    }


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(2000)

            //TODO: Check user state in AppHelper, if any, save it to AppState

            navController.navigate(NavRoute.FlowSelection.route) {
                popUpToTop(navController)
            }
        }
    }
}