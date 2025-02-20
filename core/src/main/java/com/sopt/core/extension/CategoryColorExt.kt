package com.sopt.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CategoryType

@Composable
fun CategoryType.toColor(): Color {
    return when (this) {
        CategoryType.IMPORTANT -> NoostakTheme.colors.orange
        CategoryType.SCHEDULE -> NoostakTheme.colors.blue
        CategoryType.HOBBY -> NoostakTheme.colors.purple
        CategoryType.ETC -> NoostakTheme.colors.mint
    }
}
