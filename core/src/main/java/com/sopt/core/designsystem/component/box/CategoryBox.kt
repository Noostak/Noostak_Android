package com.sopt.core.designsystem.component.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CategoryType

@Composable
fun CategoryBox(
    text: String
) {
    val context = LocalContext.current
    val categoryType = CategoryType.fromText(context, text)
    val backgroundColor = when (categoryType) {
        CategoryType.IMPORTANT -> NoostakTheme.colors.orange
        CategoryType.SCHEDULE -> NoostakTheme.colors.blue
        CategoryType.HOBBY -> NoostakTheme.colors.purple
        CategoryType.ETC -> NoostakTheme.colors.mint
    }

    Box(
        modifier = Modifier
            .padding(
                top = 5.dp,
                start = 4.dp,
                bottom = 5.dp,
                end = 3.dp
            )
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .size(13.dp)
    )
}
