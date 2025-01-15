package com.sopt.core.designsystem.component.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable


@Composable
fun NoostakSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val backgroundColor = if (checked) NoostakTheme.colors.mint else NoostakTheme.colors.gray200
    val thumbColor = NoostakTheme.colors.white

    Box(
        modifier = Modifier
            .size(width = 51.dp, height = 31.dp)
            .background(backgroundColor, RoundedCornerShape(50))
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .noRippleClickable { onCheckedChange(!checked) },
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(27.dp)
                .background(thumbColor, CircleShape)
        )
    }
}

