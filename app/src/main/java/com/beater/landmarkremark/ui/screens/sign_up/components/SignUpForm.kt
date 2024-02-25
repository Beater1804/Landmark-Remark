package com.beater.landmarkremark.ui.screens.sign_up.components

import LRPrimaryCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.beater.landmarkremark.ui.components.LRBasicTextField
import com.beater.landmarkremark.ui.components.LRRoundedButton
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.LRButtonSize
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.utils.DEFAULT_EXISTS_EMAIL
import com.beater.landmarkremark.utils.extensions.isCorrectEmailFormat
import com.beater.landmarkremark.utils.extensions.isCorrectPasswordFormat

@Composable
fun SignUpForm(
    isExistsEmail: Boolean,
    existsEmailAlert: String,
    onClearExistsEmailAlert: () -> Unit,
    onSignUp: (String, String) -> Unit
) {
    val context = LocalContext.current
    val focusManger = LocalFocusManager.current

    var email by remember {
        mutableStateOf("")
    }

    var emailValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var newPasswordValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    var newPasswordConfirmation by remember {
        mutableStateOf("")
    }

    var newPasswordConfirmationValidation by remember {
        mutableStateOf(Pair(false, ""))
    }

    fun validateEmail(): Boolean {
        if (existsEmailAlert.isNotEmpty()) {
            if (existsEmailAlert != DEFAULT_EXISTS_EMAIL && isExistsEmail) {
                emailValidation = Pair(true, existsEmailAlert)
                onClearExistsEmailAlert()
                return false
            }
        }

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
        if (newPassword.isEmpty()) {
            newPasswordValidation = Pair(true, context.getString(R.string.input_required))
            return false
        }

        if (!newPassword.isCorrectPasswordFormat()) {
            newPasswordValidation = Pair(true, context.getString(R.string.invalid_password))
            return false
        }

        newPasswordValidation = Pair(false, "")
        return true
    }

    fun validatePasswordConfirmation(): Boolean {
        if (newPasswordConfirmation.isEmpty()) {
            newPasswordConfirmationValidation =
                Pair(true, context.getString(R.string.input_required))
            return false
        }

        if (newPasswordConfirmation != newPassword) {
            newPasswordConfirmationValidation =
                Pair(true, context.getString(R.string.invalid_password_confirm))
            return false
        }

        newPasswordConfirmationValidation = Pair(false, "")
        return true
    }

    if (existsEmailAlert != DEFAULT_EXISTS_EMAIL || isExistsEmail) {
        LaunchedEffect(Unit) { validateEmail() }
    }

    fun validateForm() {
        var isValidForm = true
        if (existsEmailAlert.isNotEmpty() && existsEmailAlert != DEFAULT_EXISTS_EMAIL) {
            onClearExistsEmailAlert()
        }
        if (!validateEmail()) isValidForm = false
        if (!validatePassword()) isValidForm = false
        if (!validatePasswordConfirmation()) isValidForm = false
        if (isValidForm) onSignUp(email, newPassword)
    }

    LRPrimaryCard(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        LRText(
            text = stringResource(id = R.string.sign_up),
            fontSize = 22.sp,
            fontWeight = LRFontWeight.Bold,
            color = BlackCustom
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LRBasicTextField(
                value = email,
                placeholder = stringResource(id = R.string.email_placeholder),
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
                value = newPassword,
                placeholder = stringResource(R.string.new_password_placeholder),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions {
                    focusManger.moveFocus(FocusDirection.Down)
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = newPasswordValidation.first,
                errorMsg = newPasswordValidation.second
            ) {
                newPassword = it
            }

            LRBasicTextField(
                value = newPasswordConfirmation,
                placeholder = stringResource(R.string.confirm_new_password_placeholder),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions {
                    focusManger.clearFocus()
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = newPasswordConfirmationValidation.first,
                errorMsg = newPasswordConfirmationValidation.second
            ) {
                newPasswordConfirmation = it
            }
        }

        LRRoundedButton(
            title = stringResource(R.string.register),
            size = LRButtonSize.Primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            focusManger.clearFocus()
            validateForm()
        }
    }
}