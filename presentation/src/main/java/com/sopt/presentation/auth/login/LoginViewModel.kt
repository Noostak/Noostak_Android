package com.sopt.presentation.auth.login

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.StringRes
import com.google.android.gms.auth.api.identity.BeginSignInRequest
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
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("GoogleClientId") private val googleClientId: String
) : BaseViewModel<LoginSideEffect>() {

    private lateinit var oneTapClient: SignInClient

    fun initializeGoogleSignIn(context: Context) {
        oneTapClient = Identity.getSignInClient(context)
    }

    // Kakao Login
    fun kakaoLogin(context: Context) {
        val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                handleError(error, R.string.toast_kakao_login_failed)
            } else if (token != null) {
                handleSuccess(token.accessToken, R.string.toast_kakao_login_success)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = loginCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallback)
        }
    }

    // Google Login
    fun googleLogin(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
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
                handleError(exception, R.string.toast_google_login_failed)
            }
    }

    fun handleGoogleLoginResult(credential: SignInCredential) {
        if (!credential.googleIdToken.isNullOrEmpty()) {
            handleSuccess(credential.googleIdToken.toString(), R.string.toast_google_login_success)
        } else {
            showToast(R.string.toast_google_login_failed)
        }
    }

    private fun handleSuccess(authId: String, successMessageResId: Int) {
        showToast(successMessageResId)
        navigateToSignup(authId)
    }

    private fun handleError(error: Throwable, @StringRes errorMessageResId: Int) {
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            showToast(R.string.toast_login_cancelled)
        } else {
            showToast(errorMessageResId, error.localizedMessage.orEmpty())
        }
    }

    private fun navigateToSignup(authId: String) {
        emitSideEffect(LoginSideEffect.NavigateSignUp(authId))
    }

    private fun navigateToHome() {
        emitSideEffect(LoginSideEffect.NavigateToHome)
    }

    private fun showToast(@StringRes messageResId: Int, vararg formatArgs: String) {
        emitSideEffect(
            LoginSideEffect.ShowToast(
                message = messageResId,
                args = formatArgs.joinToString(separator = ", ")
            )
        )
    }
}
