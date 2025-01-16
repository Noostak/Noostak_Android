package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CategoryType

@Composable
fun NoostakCategoryChip(
    text: String
) {
    val context = LocalContext.current
    val categoryType = CategoryType.fromText(context, text)
    val resolvedBackgroundColor = when (categoryType) {
        CategoryType.IMPORTANT -> NoostakTheme.colors.orange
        CategoryType.SCHEDULE -> NoostakTheme.colors.blue
        CategoryType.HOBBY -> NoostakTheme.colors.purple
        CategoryType.ETC -> NoostakTheme.colors.mint
    }

    NoostakChip(
        text = text,
        textStyle = NoostakTheme.typography.c3SemiBold,
        textColor = NoostakTheme.colors.white,
        backgroundColor = resolvedBackgroundColor,
        borderColor = Color.Transparent,
        horizontalPaddingValues = dimensionResource(id = R.dimen.category_chip_horizontal_padding),
        verticalPaddingValues = dimensionResource(id = R.dimen.category_chip_vertical_padding)
    )
}
