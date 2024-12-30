package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakUserChip(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    borderColor: Color
) {
    NoostakChip(
        text = text,
        textStyle = NoostakTheme.typography.c3Regular,
        textColor = textColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        horizontalPaddingValues = 8.dp,
        verticalPaddingValues = 6.dp
    )
}
