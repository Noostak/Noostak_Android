package com.sopt.presentation.auth.login

import android.content.Context
import androidx.annotation.StringRes
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.core.type.SocialType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.UserEntity
import com.sopt.domain.repository.UserInfoRepository
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("GoogleClientId") private val googleClientId: String,
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel<LoginSideEffect>() {

    // Kakao Login
    fun kakaoLogin(context: Context) {
        val loginCallback: (OAuthToken?, Throwable?) -> Unit = this::handleKakaoLoginResult

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = loginCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallback)
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            when {
                token != null -> {
                    showToast(R.string.toast_kakao_login_success)
                    postLogin(token.accessToken, SocialType.KAKAO)
                }

                error != null -> handleError(error, R.string.toast_kakao_login_failed)
            }
        }
    }

    // Google Login
    fun googleLogin(context: Context) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(googleClientId)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            runCatching {
                val result = credentialManager.getCredential(context, request)
                handleGoogleLoginResult(result.credential)
            }.onFailure { exception ->
                handleLoginError(exception)
            }
        }
    }

    private fun handleGoogleLoginResult(credential: Credential) {
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            handleLoginSuccess(googleIdTokenCredential.id)
        } else {
            showToast(R.string.toast_google_login_failed)
        }
    }

    private fun handleLoginSuccess(token: String) {
        showToast(R.string.toast_google_login_success)
        viewModelScope.launch {
            postLogin(token, SocialType.GOOGLE)
        }
    }

    private fun handleLoginError(error: Throwable) {
        showToast(R.string.toast_google_login_failed, error.localizedMessage.orEmpty())
    }

    private fun postLogin(token: String, socialType: SocialType) {
        viewModelScope.launch {
            // TODO : 서버 연결
            checkIsNewUser(token)
        }
    }

    // 기존 사용자 확인
    private fun checkIsNewUser(authId: String) {
        viewModelScope.launch {
            if (userInfoRepository.getIsAutoLogin().first()) {
                emitSideEffect(LoginSideEffect.NavigateToHome)
            } else {
                emitSideEffect(LoginSideEffect.NavigateSignUp(authId))
                userInfoRepository.saveIsAutoLogin(true)
            }
        }
    }

    // TODO : 서버 연결 시 사용
    private fun saveUserInfo(response: UserEntity) {
        viewModelScope.launch {
            response.accessToken?.let { userInfoRepository.saveAccessToken(BEARER + it) }
            response.refreshToken?.let { userInfoRepository.saveRefreshToken(BEARER + it) }
            response.userId?.let { userInfoRepository.saveUserId(it) }
            userInfoRepository.saveIsAutoLogin(!response.accessToken.isNullOrEmpty())
        }
    }

    private fun handleError(error: Throwable, @StringRes errorMessageResId: Int) {
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            showToast(R.string.toast_login_cancelled)
        } else {
            showToast(errorMessageResId, error.localizedMessage.orEmpty())
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
    }
}
