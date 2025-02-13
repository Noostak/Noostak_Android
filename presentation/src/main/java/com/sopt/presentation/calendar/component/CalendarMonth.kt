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
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.domain.entity.DayEntity
import com.sopt.presentation.calendar.model.MonthModel
import java.time.YearMonth

@Composable
internal fun CalendarMonth(
    weeks: List<List<DayEntity>>,
    modifier: Modifier = Modifier,
    scheduleMap: Map<String, List<CalendarSchedule>> = emptyMap()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
    ) {
        weeks.forEach { week ->
            CalendarWeek(
                dayInfo = week,
                scheduleMap = scheduleMap,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CalendarMonthGroupPreview() {
    NoostakAndroidTheme {
        val monthModel = MonthModel(YearMonth.now())

        val sampleScheduleMap = mapOf(
            "2025-02-04" to listOf(
                CalendarSchedule(title = "회의", color = "#A9DBBE"),
                CalendarSchedule(title = "책 읽기", color = "#8D78D8"),
                CalendarSchedule(title = "영화 관람", color = "#3E8EFF"),
                CalendarSchedule(title = "스터디 모임", color = "#3E8EFF"),
                CalendarSchedule(title = "저녁 식사", color = "#3E8EFF")
            ),
            "2025-02-12" to listOf(
                CalendarSchedule(title = "회의", color = "#A9DBBE"),
                CalendarSchedule(title = "운동", color = "#8D78D8")
            ),
            "2025-02-13" to listOf(
                CalendarSchedule(title = "회의", color = "#A9DBBE")
            ),
            "2025-02-14" to listOf(
                CalendarSchedule(title = "출근", color = "#3E8EFF"),
                CalendarSchedule(title = "이름이 긴 약속", color = "#8D78D8")
            )
        )

        CalendarMonth(
            weeks = monthModel.calendarMonth,
            scheduleMap = sampleScheduleMap
        )
    }
}
