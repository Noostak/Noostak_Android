package com.sopt.presentation.auth.login

sealed class LoginSideEffect {
    data object NavigateToHome : LoginSideEffect()
    data class NavigateSignUp(val authId: String) : LoginSideEffect()
}