package com.sopt.presentation.auth.login

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
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

    private lateinit var oneTapClient: SignInClient

    fun initializeGoogleSignIn(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
    }

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
    fun googleLogin(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val signInRequest = createGoogleSignInRequest()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher.launch(IntentSenderRequest.Builder(result.pendingIntent).build())
            }
            .addOnFailureListener { exception ->
                handleError(exception, R.string.toast_google_login_failed)
            }
    }

    private fun createGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(googleClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun handleGoogleLoginResult(credential: SignInCredential) {
        viewModelScope.launch {
            if (!credential.googleIdToken.isNullOrEmpty()) {
                postLogin(credential.googleIdToken.toString(), SocialType.GOOGLE)
                showToast(R.string.toast_google_login_success)
            } else {
                showToast(R.string.toast_google_login_failed)
            }
        }
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
