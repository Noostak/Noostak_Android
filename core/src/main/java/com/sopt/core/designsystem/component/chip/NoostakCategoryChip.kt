package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakCategoryChip(
    text: String,
    backgroundColor: Color = NoostakTheme.colors.orange
) {
    NoostakChip(
        text = text,
        textStyle = NoostakTheme.typography.c2SemiBold,
        textColor = NoostakTheme.colors.white,
        backgroundColor = when (text) {
            "중요" -> NoostakTheme.colors.orange
            "일정" -> NoostakTheme.colors.blue
            "취미" -> NoostakTheme.colors.purple
            "기타" -> NoostakTheme.colors.mint
            else -> backgroundColor
        },
        borderColor = Color.Transparent,
        horizontalPaddingValues = dimensionResource(id = R.dimen.category_chip_horizontal_padding),
        verticalPaddingValues = dimensionResource(id = R.dimen.category_chip_vertical_padding)
    )
}
