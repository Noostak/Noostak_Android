package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.isToday
import com.sopt.domain.entity.DayEntity
import java.time.LocalDate

@Composable
internal fun CalendarWeek(
    dayInfo: List<DayEntity>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        dayInfo.forEach { dayInfo ->
            CalendarDayItem(
                dayInfo,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CalendarDayItem(dayInfo: DayEntity, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarDay(
            dayInfo = dayInfo,
            isToday = dayInfo.day.isToday(),
        )
    }
}

@Preview
@Composable
private fun CalendarWeekPreview() {
    NoostakAndroidTheme {
        CalendarWeek(
            dayInfo = List(7) { index ->
                DayEntity(
                    day = LocalDate.now().minusDays(3 - index.toLong()),
                    isOtherMonth = false
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(NoostakTheme.colors.white)
        )
    }
}
