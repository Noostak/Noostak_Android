package com.sopt.core.designsystem.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakEmptyScreen(@StringRes emptyText: Int, color: Color, style: TextStyle) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(emptyText),
            color = color,
            style = style,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakEmptyScreenPreview() {
    NoostakAndroidTheme {
        NoostakEmptyScreen(
            emptyText = R.string.text_dialog_type_group_content,
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.b4Regular
        )
    }
}
