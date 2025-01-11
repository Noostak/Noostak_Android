package com.sopt.presentation.auth.login

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
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
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("GoogleClientId") private val googleClientId: String
) : BaseViewModel<LoginSideEffect>() {
    private val _authId = MutableStateFlow("")
    val authId: StateFlow<String> = _authId
    private lateinit var oneTapClient: SignInClient

    fun initializeGoogleSignIn(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
    }

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

    // Google Login
    fun googleLogin(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val signInRequest = com.google.android.gms.auth.api.identity.BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(googleClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher.launch(IntentSenderRequest.Builder(result.pendingIntent).build())
            }
            .addOnFailureListener { exception ->
                emitSideEffect(
                    LoginSideEffect.ShowToast(
                        R.string.toast_google_login_failed,
                        exception.localizedMessage
                    )
                )
            }
    }

    fun handleGoogleLoginResult(credential: SignInCredential) {
        if (!credential.googleIdToken.isNullOrEmpty()) {
            _authId.value = credential.googleIdToken.toString()
            emitSideEffect(LoginSideEffect.ShowToast(R.string.toast_google_login_success))
            navigateToSignup(_authId.value)
        } else {
            emitSideEffect(LoginSideEffect.ShowToast(R.string.toast_google_login_failed))
        }
    }

    private fun navigateToSignup(authId: String) {
        emitSideEffect(LoginSideEffect.NavigateSignUp(authId))
    }

    private fun navigateToHome() {
        emitSideEffect(LoginSideEffect.NavigateToHome)
    }
}
