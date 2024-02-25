package com.beater.landmarkremark.view_models

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.services.firebase_services.AuthenticationService
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.DEFAULT_EMAIL_OR_PASSWORD_INCORRECT
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel : ViewModel() {
    private val authenticationService: AuthenticationService = AuthenticationService()

    private var _emailOrPasswordIncorrect = mutableStateOf(DEFAULT_EMAIL_OR_PASSWORD_INCORRECT)
    val emailOrPasswordIncorrect get() = _emailOrPasswordIncorrect
    private var _signInUiState = MutableStateFlow<UiState<Nothing>>(UiState.Default)
    val signInUiState get() = _signInUiState

    fun signIn(context: Context, email: String, password: String) {
        _signInUiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    authenticationService.signIn(email = email, password = password)
                    { isSuccessful ->
                        if (isSuccessful) {
                            AppState.currentUser = Firebase.auth.currentUser
                            _signInUiState.value = UiState.Success()
                            //TODO: Save user state in AppHelper and AppState
                        } else {
                            _emailOrPasswordIncorrect.value =
                                context.getString(R.string.email_or_password_is_incorrect)
                            _signInUiState.value = UiState.Error.onException()
                        }
                    }
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    UiState.Error.onException()
                }
            }
        }
    }

    fun resetEmailOrPasswordIncorrect() {
        _emailOrPasswordIncorrect.value = DEFAULT_EMAIL_OR_PASSWORD_INCORRECT
    }

    fun resetSignInUiState() {
        _signInUiState.value = UiState.Default
    }
}