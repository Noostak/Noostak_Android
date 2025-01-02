package com.sopt.presentation.auth.login

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginSideEffect>() {

    fun navigateToSignup() {
        emitSideEffect(LoginSideEffect.NavigateSignUp("authId"))
    }

    fun navigateToHome() {
        emitSideEffect(LoginSideEffect.NavigateToHome)
    }
}