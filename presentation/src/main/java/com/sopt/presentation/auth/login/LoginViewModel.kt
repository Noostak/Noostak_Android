package com.sopt.presentation.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AuthTypeEntity
import com.sopt.domain.repository.UserInfoRepository
import com.sopt.domain.usecase.PostSocialLoginUseCase
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("GoogleClientId") private val googleClientId: String,
    private val userInfoRepository: UserInfoRepository,
    private val postSocialLoginUseCase: PostSocialLoginUseCase
) : BaseViewModel<LoginSideEffect>() {

    private val _showDialog = MutableStateFlow(Pair(DialogType.NETWORK_LOGIN_GOOGLE_FAILURE, false))
    val showDialog: StateFlow<Pair<DialogType, Boolean>> get() = _showDialog

    private lateinit var googleSignInClient: GoogleSignInClient

    fun showDialog(dialogType: DialogType, isVisible: Boolean) {
        _showDialog.update { it.copy(first = dialogType, second = isVisible) }
    }

    // Kakao Login
    fun kakaoLogin(context: Context) {
        val loginCallback: (OAuthToken?, Throwable?) -> Unit = this::handleKakaoLoginResult
        with(UserApiClient.instance) {
            if (isKakaoTalkLoginAvailable(context)) {
                loginWithKakaoTalk(context, callback = loginCallback)
            } else {
                loginWithKakaoAccount(context, callback = loginCallback)
            }
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            token?.let {
                postSocialLogin(
                    BEARER + it.accessToken,
                    KAKAO
                )
                showToast(R.string.toast_kakao_login_success)
            } ?: run {
                handleError(error, R.string.toast_kakao_login_failed)
                showDialog(DialogType.NETWORK_LOGIN_KAKAO_FAILURE, true)
            }
        }
    }

    // Google Login
    fun getGoogleSignInIntent(activity: Activity): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(googleClientId, true)
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        return googleSignInClient.signInIntent
    }

    fun handleGoogleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        viewModelScope.launch {
            runCatching {
                val account = task.getResult(ApiException::class.java)
                val authCode = account?.serverAuthCode

                if (!authCode.isNullOrBlank()) {
                    postSocialLogin(BEARER + authCode, GOOGLE)
                    showToast(R.string.toast_google_login_success)
                } else {
                    showDialog(DialogType.NETWORK_LOGIN_GOOGLE_FAILURE, true)
                }
            }.onFailure {
                handleError(it, R.string.toast_google_login_failed)
                showDialog(DialogType.NETWORK_LOGIN_GOOGLE_FAILURE, true)
            }
        }
    }

    private fun handleError(error: Throwable?, @StringRes errorMessageResId: Int) {
        when {
            // 카카오 로그인 취소
            error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                showToast(R.string.toast_login_cancelled)
            }
            // 구글 로그인 취소
            error?.message?.contains(CANCELLED, ignoreCase = true) == true -> {
                showToast(R.string.toast_login_cancelled)
            }

            else -> {
                val errorMessage = error?.localizedMessage.orEmpty()
                showToast(errorMessageResId, errorMessage)
            }
        }
    }

    // 소셜 로그인
    private fun postSocialLogin(accessToken: String, socialType: String) {
        viewModelScope.launch {
            postSocialLoginUseCase(accessToken, AuthTypeEntity(socialType)).fold(
                onSuccess = { response ->
                    saveTokens(response.accessToken, response.refreshToken)
                    userInfoRepository.saveMemberId(response.memberId)
                    userInfoRepository.saveIsAutoLogin(true)
                    emitSideEffect(LoginSideEffect.NavigateToHome)
                },
                onFailure = { error ->
                    emitSideEffect(LoginSideEffect.NavigateToOnboarding(accessToken, socialType))
                }
            )
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            userInfoRepository.saveAccessToken(BEARER + accessToken)
            userInfoRepository.saveRefreshToken(BEARER + refreshToken)
        }
    }

    private fun showToast(@StringRes messageResId: Int, vararg formatArgs: String) {
        emitSideEffect(
            LoginSideEffect.ShowToast(
                message = messageResId,
                args = formatArgs.joinToString(separator = ", ")
            )
        )
    }

    companion object {
        private const val BEARER = "Bearer "
        private const val CANCELLED = "CANCELLED"
        private const val KAKAO = "KAKAO"
        private const val GOOGLE = "GOOGLE"
    }
}
