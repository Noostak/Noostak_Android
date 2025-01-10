package com.sopt.core.designsystem.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakSubHeaderText(text: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = text,
        style = NoostakTheme.typography.c2SemiBold,
        color = NoostakTheme.colors.gray900
    )
}