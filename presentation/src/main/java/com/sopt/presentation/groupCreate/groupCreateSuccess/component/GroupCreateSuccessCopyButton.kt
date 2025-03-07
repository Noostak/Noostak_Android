package com.sopt.presentation.groupCreate.groupCreateSuccess.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R

@Composable
fun GroupCreateSuccessCopyButton(onClick: () -> Unit = {}) {
    Column(modifier = Modifier.noRippleClickable { onClick() }) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(R.string.text_group_create_success_code_copy),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.c3Regular
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = NoostakTheme.colors.gray800)
                    .height(1.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateSuccessCopyButtonPreview() {
    NoostakAndroidTheme {
        GroupCreateSuccessCopyButton()
    }
}
