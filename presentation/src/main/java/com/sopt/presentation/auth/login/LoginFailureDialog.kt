package com.sopt.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.dialog.BaseDialog
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable

@Composable
fun LoginFailureDialog(
    onRetryRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    description: String,
    retryText: String,
    dismissText: String
) {
    BaseDialog(
        onDismissRequest = onDismissRequest,
        radius = 20.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.padding(horizontal = 35.dp),
                text = description,
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.c3Regular,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = NoostakTheme.colors.gray200
            )
            Row(
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable { onDismissRequest() },
                    text = dismissText,
                    color = NoostakTheme.colors.gray900,
                    textAlign = TextAlign.Center,
                    style = NoostakTheme.typography.c2SemiBold
                )
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = NoostakTheme.colors.gray200
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable { onRetryRequest() },
                    text = retryText,
                    color = NoostakTheme.colors.blue,
                    textAlign = TextAlign.Center,
                    style = NoostakTheme.typography.c2SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorDialogPreview() {
    NoostakAndroidTheme {
        LoginFailureDialog(
            onRetryRequest = {},
            onDismissRequest = {},
            description = "네트워크 문제로\n카카오톡 로그인을 연결하지 못했습니다.\n다시 시도하시겠습니까?",
            retryText = "재시도",
            dismissText = "취소"
        )
    }
}
