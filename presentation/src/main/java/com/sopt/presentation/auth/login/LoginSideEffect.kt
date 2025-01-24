package com.sopt.presentation.auth.login

import androidx.annotation.StringRes

sealed class LoginSideEffect {
    data object NavigateToHome : LoginSideEffect()
    data class NavigateToOnboarding(val authId: String) : LoginSideEffect()
    data class ShowToast(
        @StringRes val message: Int,
        val args: String? = null
    ) : LoginSideEffect()
}
