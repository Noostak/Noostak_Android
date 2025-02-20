package com.sopt.core.designsystem.component.timepicker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.NoostakTheme.colors
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.timepicker.TimePicker

@Composable
fun TimeDisplay(
    label: String,
    hour: Int,
    minute: Int = 0,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) colors.blue600 else colors.gray900

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.noRippleClickable {
            onClick()
        }
    ) {
        Text(
            text = label,
            style = NoostakTheme.typography.c3Regular,
            color = colors.gray700
        )
        Text(
            text = TimePicker().timeFormat(hour, minute),
            style = NoostakTheme.typography.h1Bold.copy(color = textColor)
        )
    }
}
