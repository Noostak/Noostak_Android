package com.sopt.presentation.auth.signup.inputGroupCode

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.textfield.OtpInputField
import com.sopt.core.designsystem.component.topappbar.NoostakCloseAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun InputGroupCodeRoute(
    navigateToGroup: () -> Unit,
    navigateUp: () -> Unit,
    inputGroupCodeViewModel: InputGroupCodeViewModel = hiltViewModel()
) {
    LaunchedEffect(inputGroupCodeViewModel.sideEffects) {
        inputGroupCodeViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is InputGroupCodeSideEffect.NavigateUp -> navigateUp()
                is InputGroupCodeSideEffect.NavigateToGroup -> navigateToGroup()
            }
        }
    }

    InputGroupCodeScreen(
        onBackButtonClick = inputGroupCodeViewModel::navigateUp,
        onCheckGroupCodeClick = inputGroupCodeViewModel::navigateToGroup
    )
}

@Composable
fun InputGroupCodeScreen(
    onBackButtonClick: () -> Unit,
    onCheckGroupCodeClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var groupCode by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        topBar = {
            NoostakCloseAppBar(
                modifier = Modifier,
                onBackButtonClick = {
                    onBackButtonClick()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.horizontal_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(147.dp))
            Text(
                text = stringResource(R.string.tv_input_code_description),
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.t1SemiBold.copy(
                    lineHeight = 29.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )
            OtpInputField(
                otpText = groupCode,
                onOtpTextChange = { otp, isComplete ->
                    groupCode = otp
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                text = stringResource(R.string.btn_invite_code_confirm),
                isEnabled = groupCode.length == 6,
                onButtonClick = onCheckGroupCodeClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputGroupCodePreview() {
    NoostakAndroidTheme {
        InputGroupCodeScreen(
            onBackButtonClick = {},
            onCheckGroupCodeClick = {}
        )
    }
}
