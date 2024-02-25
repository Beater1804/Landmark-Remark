package com.beater.landmarkremark.models.ui_actions

sealed class SignInFormAction {
    data class OnLogin(val email: String, val password: String) : SignInFormAction()
}

typealias SignInFormCallback = (SignInFormAction) -> Unit
