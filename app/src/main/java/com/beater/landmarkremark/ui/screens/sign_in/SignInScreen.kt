package com.beater.landmarkremark.ui.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.beater.landmarkremark.models.ui_actions.SignInFormAction
import com.beater.landmarkremark.ui.components.LRLoading
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.screens.LRCommonScreen
import com.beater.landmarkremark.ui.screens.sign_in.components.SignInForm
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Blue
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.DEFAULT_EMAIL_OR_PASSWORD_INCORRECT
import com.beater.landmarkremark.utils.extensions.noRippleClickable
import com.beater.landmarkremark.utils.extensions.showToast
import com.beater.landmarkremark.view_models.SignInViewModel

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val emailOrPasswordIncorrect = viewModel.emailOrPasswordIncorrect
    val signInUiState = viewModel.signInUiState.collectAsState().value

    val isEmailOrPasswordIncorrectState by remember {
        derivedStateOf { emailOrPasswordIncorrect.value != DEFAULT_EMAIL_OR_PASSWORD_INCORRECT }
    }

    val emailOrPasswordIncorrectState by remember {
        derivedStateOf { emailOrPasswordIncorrect.value }
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
                text = stringResource(R.string.back),
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

            SignInForm(
                isEmailOrPasswordIncorrect = isEmailOrPasswordIncorrectState,
                emailOrPasswordIncorrect = emailOrPasswordIncorrectState,
                onClearEmailOrPasswordIncorrect = {
                    viewModel.resetEmailOrPasswordIncorrect()
                }
            ) { action ->
                when (action) {
                    is SignInFormAction.OnLogin -> {
                        viewModel.signIn(
                            context = context,
                            email = action.email,
                            password = action.password
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    when (signInUiState) {
        is UiState.Error<*> -> {
            when (val error = signInUiState.errorType) {
                is ServiceError.Specific -> context.showToast(error.errorMsg)
            }
            viewModel.resetSignInUiState()
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