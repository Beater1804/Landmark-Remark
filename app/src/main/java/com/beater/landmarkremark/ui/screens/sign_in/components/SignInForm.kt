package com.beater.landmarkremark.ui.screens.sign_in.components

import LRPrimaryCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.ui_actions.SignInFormAction
import com.beater.landmarkremark.models.ui_actions.SignInFormCallback
import com.beater.landmarkremark.ui.components.LRBasicTextField
import com.beater.landmarkremark.ui.components.LRRoundedButton
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.LRButtonSize
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.Red2
import com.beater.landmarkremark.utils.DEFAULT_EMAIL_OR_PASSWORD_INCORRECT
import com.beater.landmarkremark.utils.extensions.isCorrectEmailFormat
import com.beater.landmarkremark.utils.extensions.isCorrectPasswordFormat

@Composable
fun SignInForm(
    isEmailOrPasswordIncorrect: Boolean,
    emailOrPasswordIncorrect: String,
    onClearEmailOrPasswordIncorrect: () -> Unit,
    onAction: SignInFormCallback
) {
    val context = LocalContext.current
    val focusManger = LocalFocusManager.current

    var email by remember {
        mutableStateOf("")
    }

    var emailValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    fun validateEmail(): Boolean {
        if (email.isEmpty()) {
            emailValidation = Pair(true, context.getString(R.string.input_required))
            return false
        }

        if (!email.isCorrectEmailFormat()) {
            emailValidation = Pair(true, context.getString(R.string.invalid_email))
            return false
        }

        emailValidation = Pair(false, "")
        return true
    }

    fun validatePassword(): Boolean {
        if (password.isEmpty()) {
            passwordValidation = Pair(true, context.getString(R.string.input_required))
            return false
        }

        if (!password.isCorrectPasswordFormat()) {
            passwordValidation = Pair(true, context.getString(R.string.invalid_password))
            return false
        }

        passwordValidation = Pair(false, "")
        return true
    }

    val emptyInformationValidation by remember {
        derivedStateOf { (email.isEmpty() || password.isEmpty()) }
    }

    val passwordCorrectFormat by remember {
        derivedStateOf {
            password.isCorrectPasswordFormat()
        }
    }

    var formValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    fun validateForm(): Boolean {
        if (emailOrPasswordIncorrect.isNotEmpty()) {
            if (emailOrPasswordIncorrect != DEFAULT_EMAIL_OR_PASSWORD_INCORRECT && isEmailOrPasswordIncorrect) {
                formValidation = Pair(true, emailOrPasswordIncorrect)
                onClearEmailOrPasswordIncorrect()
                return false
            }
        }

        if (emptyInformationValidation) {
            formValidation = Pair(true, context.getString(R.string.sign_in_input_required))
            return false
        }


        formValidation = Pair(false, "")
        return true
    }

    if (emailOrPasswordIncorrect != DEFAULT_EMAIL_OR_PASSWORD_INCORRECT && isEmailOrPasswordIncorrect) {
        LaunchedEffect(Unit) { validateForm() }
    }

    fun validateEntireForm() {
        var isValidForm = true
        if (emailOrPasswordIncorrect.isNotEmpty() && emailOrPasswordIncorrect != DEFAULT_EMAIL_OR_PASSWORD_INCORRECT) {
            onClearEmailOrPasswordIncorrect()
        }
        if (!validateEmail()) isValidForm = false
        if (!validatePassword()) isValidForm = false
        if (!validateForm()) isValidForm = false
        if (isValidForm) onAction(SignInFormAction.OnLogin(email = email, password = password))
    }

    LRPrimaryCard(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        LRText(
            text = stringResource(R.string.sign_in_to_landmark_remark),
            fontSize = 22.sp,
            fontWeight = LRFontWeight.Bold,
            color = BlackCustom
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LRBasicTextField(
                value = email,
                placeholder = stringResource(R.string.email_placeholder),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions {
                    focusManger.moveFocus(FocusDirection.Down)
                },
                isError = emailValidation.first,
                errorMsg = emailValidation.second
            ) {
                email = it
            }

            LRBasicTextField(
                value = password,
                placeholder = stringResource(R.string.password_placeholder),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions {
                    focusManger.clearFocus()
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordValidation.first,
                errorMsg = passwordValidation.second
            ) {
                password = it
            }

            if (!emptyInformationValidation && passwordCorrectFormat && formValidation.first) {
                LRText(
                    text = formValidation.second,
                    color = Red2,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 8.dp,
                        end = 24.dp,
                        bottom = 16.dp
                    )
                )
            }
        }

        LRRoundedButton(
            title = stringResource(id = R.string.sign_in),
            size = LRButtonSize.Primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            focusManger.clearFocus()

            validateEntireForm()
        }
    }
}