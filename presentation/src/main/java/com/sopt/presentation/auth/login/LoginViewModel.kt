package com.sopt.presentation.auth.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginSideEffect>() {

    private val _authId = MutableStateFlow("")
    val authId: StateFlow<String> = _authId

    // Kakao Login
    fun kakaoLogin(context: Context) {
        val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            handleKakaoLoginResult(token, error)
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = loginCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallback)
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
        when {
            error != null -> {
                handleKakaoError(error)
            }

            token != null -> {
                _authId.value = token.accessToken
                emitSideEffect(LoginSideEffect.ShowToast(R.string.toast_kakao_login_success))
                navigateToSignup(_authId.value)
            }
        }
    }

    private fun handleKakaoError(error: Throwable) {
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            emitSideEffect(LoginSideEffect.ShowToast(R.string.toast_login_cancelled))
        } else {
            emitSideEffect(LoginSideEffect.ShowToast(R.string.toast_kakao_login_failed))
        }
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
