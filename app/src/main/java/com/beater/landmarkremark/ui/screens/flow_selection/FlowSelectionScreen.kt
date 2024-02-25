package com.beater.landmarkremark.ui.screens.flow_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.ui.components.LRRoundedButton
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.theme.LRButtonSize
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.LRRoundedButtonStyle
import com.beater.landmarkremark.view_models.FlowSelectionViewModel

@Composable
fun FlowSelectionScreen(
    navController: NavHostController,
    viewModel: FlowSelectionViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                LRText(
                    text = stringResource(R.string.welcome, stringResource(id = R.string.app_name)),
                    fontWeight = LRFontWeight.Bold,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            LRRoundedButton(
                title = stringResource(R.string.sign_in),
                size = LRButtonSize.Primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                navController.navigate(NavRoute.SignIn.route)
            }

            LRRoundedButton(
                title = stringResource(R.string.sign_up),
                size = LRButtonSize.Primary,
                style = LRRoundedButtonStyle.Negative,
                modifier = Modifier.fillMaxWidth()
            ) {
                navController.navigate(NavRoute.SignUp.route)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}