package com.sopt.presentation.auth.signup.inputGroupCode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R
import com.sopt.presentation.auth.component.AuthButton
import com.sopt.presentation.auth.component.OtpInputField

@Composable
fun InputGroupCodeRoute(
    navigateToGroup: () -> Unit,
    navigateUp: () -> Unit,
    inputGroupCodeViewModel: InputGroupCodeViewModel = hiltViewModel(),
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
    onCheckGroupCodeClick: () -> Unit,
) {
    var groupCode by remember { mutableStateOf("") }
    val isNextEnabled = groupCode.length == 6

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                modifier = Modifier,
                isIconVisible = true,
                onBackButtonClick = onBackButtonClick
            )
        },
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
                style = NoostakTheme.typography.t1SemiBold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            OtpInputField(
                otpText = groupCode,
                onOtpTextChange = { otp, isComplete ->
                    groupCode = otp
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AuthButton(
                padding = PaddingValues(vertical = 15.dp),
                onClick = onCheckGroupCodeClick,
                isEnabled = isNextEnabled,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.btn_submit),
                    style = NoostakTheme.typography.t3Bold
                )
            }
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