package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakCategoryChip(
    text: String,
    backgroundColor: Color
) {
    NoostakChip(
        text = text,
        textStyle = NoostakTheme.typography.c2SemiBold,
        textColor = NoostakTheme.colors.white,
        backgroundColor = backgroundColor,
        borderColor = Color.Transparent,
        horizontalPaddingValues = 15.dp,
        verticalPaddingValues = 6.dp
    )
}
