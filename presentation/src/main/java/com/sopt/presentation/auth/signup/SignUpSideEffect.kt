package com.sopt.presentation.auth.signup

import androidx.annotation.StringRes

sealed class SignUpSideEffect {
    data class NavigateToCheckInvite(val name: String) : SignUpSideEffect()
    data class ShowToast(@StringRes val message: Int) : SignUpSideEffect()
}