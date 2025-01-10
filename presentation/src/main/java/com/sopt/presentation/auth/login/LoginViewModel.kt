package com.sopt.presentation.auth.login

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginSideEffect>() {

    private val _authId = MutableStateFlow("")
    val authId: StateFlow<String> = _authId

    fun kakaoLogin() {
        // TODO: 카카오 로그인
        navigateToHome()
    }

    fun googleLogin() {
        // TODO: 구글 로그인
        _authId.value = "google_access_token"
        navigateToSignup(_authId.value)
    }

    private fun navigateToSignup(authId: String) {
        emitSideEffect(LoginSideEffect.NavigateSignUp(authId))
    }

    private fun navigateToHome() {
        emitSideEffect(LoginSideEffect.NavigateToHome)
    }
}
