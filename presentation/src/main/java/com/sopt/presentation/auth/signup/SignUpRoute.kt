package com.sopt.presentation.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.image.ProfileImagePicker
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.toast
import com.sopt.core.type.TextFieldType
import com.sopt.presentation.R

@Composable
fun SignUpRoute(
    authId: String,
    navigateToCheckInvite: (String) -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val signUpState by signUpViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = authId) {
        signUpViewModel.updateAuthId(authId)
    }

    LaunchedEffect(signUpViewModel.sideEffects, lifecycleOwner) {
        signUpViewModel.sideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignUpSideEffect.NavigateToCheckInvite -> navigateToCheckInvite(sideEffect.name)
                    is SignUpSideEffect.ShowToast -> context.toast(sideEffect.message)
                }
            }
    }

    SignUpScreen(
        signUpState = signUpState,
        onProfileEditBtnClick = { /* Handle profile edit */ },
        onSignUpClick = signUpViewModel::navigateToCheckInvite,
        onNameChange = signUpViewModel::updateName
    )
}


@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onProfileEditBtnClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.horizontal_padding)),
    ) {
        Text(
            text = stringResource(R.string.tv_signup_profile),
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.h2Bold,
            modifier = Modifier.padding(top = 70.dp)
        )
        ProfileImagePicker(
            selectedImageUri = signUpState.profileImage,
            onCameraBtnClick = onProfileEditBtnClick,
            modifier = Modifier
                .padding(top = 46.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(27.dp))
        NoostakTextField(
            textFieldType = TextFieldType.SIGNUP,
            value = signUpState.name,
            maxLength = 10,
            onValueChange = { onNameChange(it) }
        )
        Spacer(modifier = Modifier.weight(1f))
        NoostakBottomButton(
            text = stringResource(R.string.btn_next),
            isEnabled = signUpState.name.isNotEmpty(),
            onButtonClick = onSignUpClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    NoostakAndroidTheme {
        SignUpScreen(
            signUpState = SignUpState(
                name = "Preview Name",
                profileImage = null
            ),
            onProfileEditBtnClick = {},
            onNameChange = {},
            onSignUpClick = {},
        )
    }
}