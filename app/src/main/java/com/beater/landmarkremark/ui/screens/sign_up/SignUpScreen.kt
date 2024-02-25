package com.beater.landmarkremark.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.ServiceError
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.ui.components.LRLoading
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.screens.LRCommonScreen
import com.beater.landmarkremark.ui.screens.sign_up.components.SignUpForm
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.DEFAULT_EXISTS_EMAIL
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.extensions.popUpToTop
import com.beater.landmarkremark.utils.extensions.showToast
import com.beater.landmarkremark.view_models.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val existsEmailAlert = viewModel.existsEmailAlert
    val signUpUiState = viewModel.signUpUiState.collectAsState().value

    val isExistEmailState by remember {
        derivedStateOf { existsEmailAlert.value != DEFAULT_EXISTS_EMAIL }
    }

    val existsEmailAlertState by remember {
        derivedStateOf { existsEmailAlert.value }
    }

    LRCommonScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LRText(
                text = stringResource(id = R.string.back),
                fontSize = 22.sp,
                fontWeight = LRFontWeight.Bold,
                color = BlackCustom,
                modifier = Modifier
                    .align(Alignment.Start)
                    .noRippleClickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignUpForm(
                isExistsEmail = isExistEmailState,
                existsEmailAlert = existsEmailAlertState,
                onClearExistsEmailAlert = {
                    viewModel.resetExistsEmailAlert()
                },
                onSignUp = { newEmail, newPassword ->
                    viewModel.signUp(
                        context = context,
                        email = newEmail,
                        password = newPassword
                    )
                }
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    when (signUpUiState) {
        is UiState.Error<*> -> {
            when (val error = signUpUiState.errorType) {
                is ServiceError.Specific -> context.showToast(stringResource(R.string.error))
            }
            viewModel.resetSignUpUiState()
        }

        UiState.Loading -> LRLoading()
        is UiState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate(NavRoute.MyNotes.route) {
                    popUpTo(0)
                }
            }
        }

        else -> {}
    }
}