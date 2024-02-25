package com.beater.landmarkremark.view_models

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.services.firebase_services.AuthenticationService
import com.beater.landmarkremark.utils.AppState
import com.beater.landmarkremark.utils.DEFAULT_EXISTS_EMAIL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel() {
    private val authenticationService: AuthenticationService = AuthenticationService()

    private var _existsEmailAlert = mutableStateOf(DEFAULT_EXISTS_EMAIL)
    val existsEmailAlert get() = _existsEmailAlert
    private var _signUpUiState = MutableStateFlow<UiState<Nothing>>(UiState.Default)
    val signUpUiState get() = _signUpUiState

    fun signUp(context: Context, email: String, password: String) {
        _signUpUiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    authenticationService.signUp(email = email, password = password)
                    { isSuccessful ->
                        if (isSuccessful) {
                            AppState.currentUser = Firebase.auth.currentUser
                            _signUpUiState.value = UiState.Success()
                            //TODO: Save user state in AppHelper and AppState
                        } else {
                            _existsEmailAlert.value =
                                context.getString(R.string.email_already_exists)
                            _signUpUiState.value = UiState.Error.onException()
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

    fun resetExistsEmailAlert() {
        _existsEmailAlert.value = DEFAULT_EXISTS_EMAIL
    }

    fun resetSignUpUiState() {
        _signUpUiState.value = UiState.Default
    }
}