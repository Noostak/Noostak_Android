package com.sopt.presentation.auth.login

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.Black
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.White
import com.sopt.core.designsystem.theme.Yellow
import com.sopt.presentation.R
import com.sopt.presentation.auth.component.AuthButton

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToSignUp: (String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    LaunchedEffect(loginViewModel.sideEffects) {
        loginViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.NavigateToHome -> navigateToHome()
                is LoginSideEffect.NavigateSignUp -> navigateToSignUp(sideEffect.authId)
            }
        }
    }

    LoginScreen(
        onKaKaoLoginClick = {
            loginViewModel.navigateToHome()
        },
        onGoogleLoginCLick = {
            loginViewModel.navigateToSignup()
        }
    )
}

@Composable
fun LoginScreen(
    onKaKaoLoginClick: () -> Unit,
    onGoogleLoginCLick: () -> Unit
) {
    val offsetY = remember { Animatable(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            onKaKaoLoginCLick = onKaKaoLoginClick,
            onGoogleLoginCLick = onGoogleLoginCLick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun SocialLoginBottom(
    onKaKaoLoginCLick: () -> Unit,
    onGoogleLoginCLick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tv_login_description),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.c3Regular
        )
        Spacer(modifier = Modifier.height(8.dp))
        AuthButton(
            padding = PaddingValues(vertical = 13.dp),
            onClick = onKaKaoLoginCLick,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Yellow,
            contentColor = Black
        ) {
            Text(
                text = stringResource(R.string.btn_login_kakao),
                style = NoostakTheme.typography.b1SemiBold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        AuthButton(
            padding = PaddingValues(vertical = 13.dp),
            onClick = onGoogleLoginCLick,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Black,
            contentColor = White
        ) {
            Text(
                text = stringResource(R.string.btn_login_google),
                style = NoostakTheme.typography.b1SemiBold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    NoostakAndroidTheme {
        LoginScreen(
            onKaKaoLoginClick = {},
            onGoogleLoginCLick = {}
        )
    }
}