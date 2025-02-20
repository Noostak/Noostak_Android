package com.sopt.presentation.calendar.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CategoryType

@Composable
fun ScheduleColorChip(category: String) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .size(width = 6.dp, height = 38.dp)
            .clip(RoundedCornerShape(31.dp))
            .background(
                color = when (CategoryType.fromText(context, category)) {
                    CategoryType.IMPORTANT -> NoostakTheme.colors.orange
                    CategoryType.SCHEDULE -> NoostakTheme.colors.blue
                    CategoryType.HOBBY -> NoostakTheme.colors.purple
                    CategoryType.ETC -> NoostakTheme.colors.mint
                }
            )
    )
}
