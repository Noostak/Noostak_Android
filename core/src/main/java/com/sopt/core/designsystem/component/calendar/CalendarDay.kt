package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.DayEntity
import java.time.LocalDate

@Composable
fun CalendarDay(
    dayInfo: DayEntity,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = NoostakTheme.colors

    val backgroundColor by rememberUpdatedState(
        when {
            isToday -> colors.gray900
            else -> Color.Transparent
        }
    )

    val textColor by rememberUpdatedState(
        when {
            isToday -> colors.white
            dayInfo.isOtherMonth -> colors.gray600
            else -> colors.gray900
        }
    )

    Box(
        modifier = modifier
            .size(20.dp)
            .background(color = backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayInfo.day.dayOfMonth.toString(),
            color = textColor,
            style = NoostakTheme.typography.c3Regular
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDayPreview() {
    NoostakAndroidTheme {
        Row {
            // 다른 달 날짜
            CalendarDay(
                dayInfo = DayEntity(day = LocalDate.now().minusMonths(1), isOtherMonth = true),
                isToday = false
            )
            // 현재 달 날짜
            CalendarDay(
                dayInfo = DayEntity(day = LocalDate.now(), isOtherMonth = false),
                isToday = false
            )
            // 오늘 날짜
            CalendarDay(
                dayInfo = DayEntity(day = LocalDate.now(), isOtherMonth = false),
                isToday = true
            )
        }
    }
}
