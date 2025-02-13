package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.DayEntity
import com.sopt.presentation.calendar.model.MonthModel
import java.time.YearMonth

@Composable
internal fun CalendarMonth(
    weeks: List<List<DayEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
    ) {
        weeks.forEach { week ->
            CalendarWeek(
                dayInfo = week,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CalendarMonthPreview() {
    NoostakAndroidTheme {
        val monthModel = MonthModel(YearMonth.now())

        CalendarMonth(
            weeks = monthModel.calendarMonth,
        )
    }
}
