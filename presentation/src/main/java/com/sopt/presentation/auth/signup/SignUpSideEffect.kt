package com.sopt.presentation.auth.signup

import androidx.annotation.StringRes

sealed class SignUpSideEffect {
    data object NavigateToCheckInvite : SignUpSideEffect()
    data class ShowToast(@StringRes val message: Int) : SignUpSideEffect()
}