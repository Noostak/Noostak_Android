package com.sopt.presentation.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.theme.Black
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.White
import com.sopt.core.extension.toast
import com.sopt.presentation.R
import com.sopt.presentation.auth.component.AuthButton
import com.sopt.presentation.auth.component.AuthTextField

@Composable
fun SignUpRoute(
    authId: String,
    navigateToCheckInvite: (String) -> Unit,
    signUpviewModel: SignUpViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = authId) {
        signUpviewModel.updateAuthId(authId)
    }

    LaunchedEffect(signUpviewModel.sideEffects, lifecycleOwner) {
        signUpviewModel.sideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignUpSideEffect.NavigateToCheckInvite -> navigateToCheckInvite(sideEffect.name)
                    is SignUpSideEffect.ShowToast -> context.toast(sideEffect.message)
                }
            }
    }

    SignUpScreen(
        onProfileEditClick = { },
        onSignUpClick = signUpviewModel::navigateToCheckInvite,
        onInputChange = signUpviewModel::updateName
    )
}

@Composable
fun SignUpScreen(
    onProfileEditClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onInputChange: (String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    val isNextEnabled = name.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = stringResource(R.string.tv_signup_profile),
            color = Black,
            style = NoostakTheme.typography.h2Bold
        )
        Spacer(modifier = Modifier.height(46.dp))
        ProfileImage(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onImageClick = onProfileEditClick
        )
        Spacer(modifier = Modifier.height(27.dp))
        AuthTextField(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            onTextChange = {
                name = it
                onInputChange(it)
            },
            isExampleVisible = true,
            placeholderText = stringResource(R.string.hint_signup_name),
            maxLength = 10,
            exampleText = stringResource(R.string.tv_signup_example_name),
        )
        Spacer(modifier = Modifier.weight(1f))
        AuthButton(
            padding = PaddingValues(vertical = 15.dp),
            onClick = onSignUpClick,
            isEnabled = isNextEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.btn_next),
                style = NoostakTheme.typography.t3Bold
            )
        }
    }
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    onImageClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(112.dp)
                .background(color = White, shape = CircleShape)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_profile_camera),
            contentDescription = "Camera Icon",
            modifier = Modifier
                .size(35.dp)
                .clickable { onImageClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    NoostakAndroidTheme {
        SignUpScreen(
            onSignUpClick = {},
            onProfileEditClick = {},
            onInputChange = {}
        )
    }
}