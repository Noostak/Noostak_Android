package com.sopt.core.designsystem.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.component.button.BaseButton
import com.sopt.core.designsystem.component.topappbar.NoostakCloseAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakFailureScreen(
    onBackButtonClick: () -> Unit = { },
    onRetryButtonClick: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            NoostakCloseAppBar(
                modifier = Modifier.fillMaxWidth(),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = stringResource(R.string.text_failure_header),
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.t1SemiBold
            )
            BaseButton(
                shape = RoundedCornerShape(8.dp),
                style = NoostakTheme.typography.t3Bold,
                paddingHorizontal = 20.dp,
                paddingVertical = 15.dp,
                text = stringResource(R.string.btn_failure_retry),
                onButtonClick = onRetryButtonClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakFailureScreenPreview() {
    NoostakAndroidTheme {
        NoostakFailureScreen()
    }
}
