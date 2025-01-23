package com.sopt.presentation.auth.login

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.toast
import com.sopt.presentation.R
import com.sopt.presentation.auth.component.LoginButton

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToOnboarding: (String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val showDialog by loginViewModel.showDialog.collectAsStateWithLifecycle()
    val dialogDescription by loginViewModel.dialogDescription.collectAsStateWithLifecycle()

    LaunchedEffect(loginViewModel.sideEffects) {
        loginViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.NavigateToHome -> navigateToHome()
                is LoginSideEffect.NavigateOnboarding -> navigateToOnboarding(sideEffect.authId)
                is LoginSideEffect.ShowToast -> context.toast(sideEffect.message)
            }
        }
    }

    if (showDialog) {
        LoginFailureDialog(
            onRetryRequest = { loginViewModel.googleLogin(context) },
            onDismissRequest = { loginViewModel.showFailLoginDialog(false) },
            description = stringResource(R.string.dialog_login_description, dialogDescription),
            retryText = stringResource(R.string.dialog_login_retry),
            dismissText = stringResource(R.string.dialog_login_dismiss)
        )
    }

    LoginScreen(
        onKakaoLoginClick = { loginViewModel.kakaoLogin(context) },
        onGoogleLoginClick = { loginViewModel.googleLogin(context) }
    )
}

@Composable
fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit
) {
    val offsetY = remember { Animatable(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.horizontal_padding))
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_login_logo),
            contentDescription = stringResource(R.string.iv_login_description),
            modifier = Modifier
                .offset(y = offsetY.value.dp)
                .padding(bottom = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        SocialLoginBottom(
            onKakaoLoginClick = onKakaoLoginClick,
            onGoogleLoginClick = onGoogleLoginClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun SocialLoginBottom(
    onKakaoLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tv_login_description),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.c3Regular,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LoginButton(
            text = stringResource(R.string.btn_login_kakao),
            onLoginBtnClick = onKakaoLoginClick,
            modifier = Modifier.padding(bottom = 12.dp),
            containerColor = NoostakTheme.colors.yellow,
            contentColor = NoostakTheme.colors.black
        )
        LoginButton(
            text = stringResource(R.string.btn_login_google),
            onLoginBtnClick = onGoogleLoginClick,
            containerColor = NoostakTheme.colors.black,
            contentColor = NoostakTheme.colors.white
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NoostakAndroidTheme {
        LoginScreen(
            onKakaoLoginClick = {},
            onGoogleLoginClick = {}
        )
    }
}
