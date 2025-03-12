package com.sopt.presentation.auth.login

import androidx.annotation.StringRes
import com.sopt.core.type.DialogType

sealed class LoginSideEffect {
    data object NavigateToHome : LoginSideEffect()
    data class NavigateToOnboarding(val authId: String, val socialType: String) : LoginSideEffect()
    data class ShowToast(
        @StringRes val message: Int,
        val args: String? = null
    ) : LoginSideEffect()

    data class ShowDialog(val dialogType: DialogType) : LoginSideEffect()
}
