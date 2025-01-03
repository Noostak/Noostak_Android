package com.sopt.presentation.group.groupCreateSuccess

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.topappbar.NoostakCloseAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R
import com.sopt.presentation.group.groupCreateSuccess.regex.generateRandomCode

@Composable
fun GroupCreateSuccessRoute() {
    GroupCreateSuccessScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GroupCreateSuccessScreen() {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            NoostakCloseAppBar(
                modifier = Modifier,
                onBackButtonClick = {
                    // nav to group detail page
                })
        },
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Image Group Create Success",
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .size(108.dp)
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(R.string.text_group_create_success_title),
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.t1SemiBold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                )
                Text(
                    text = stringResource(R.string.text_group_create_success_content),
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.b4Regular,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                )
                Text(
                    text = generateRandomCode(),
                    color = NoostakTheme.colors.gray800,
                    style = NoostakTheme.typography.codeMedium,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
            Text(
                text = stringResource(R.string.text_group_create_success_code_copy),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.c3Regular.copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        // copy code
                    }
                    .align(Alignment.CenterHorizontally),
            )
            NoostakBottomButton(
                text = stringResource(R.string.btn_group_create_success_code_send),
                activateColor = NoostakTheme.colors.blue600,
                deactivateColor = NoostakTheme.colors.gray500,
                isEnabled = true,
                onButtonClick = {
                    // enter clip board
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateSuccessScreenPreview() {
    NoostakAndroidTheme {
        GroupCreateSuccessScreen()
    }
}