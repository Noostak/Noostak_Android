package com.sopt.core.designsystem.component.calendar

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
import com.sopt.core.extension.getMonthDays
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.domain.entity.DayEntity
import java.time.YearMonth

@Composable
fun CalendarMonth(
    weeks: List<List<DayEntity>>,
    modifier: Modifier = Modifier,
    scheduleMap: Map<String, List<CalendarSchedule>> = emptyMap(),
    onItemClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
    ) {
        weeks.forEachIndexed { index, week ->
            CalendarWeek(
                dayInfo = week,
                scheduleMap = scheduleMap,
                modifier = Modifier.padding(top = if (index == 0) 0.dp else 4.dp),
                onItemClick = { onItemClick() }
            )
        }
    }
}

@Preview
@Composable
private fun CalendarMonthGroupPreview() {
    NoostakAndroidTheme {
        val sampleScheduleMap = mapOf(
            "2025-02-04" to listOf(
                CalendarSchedule(title = "회의", categoryType = "기타"),
                CalendarSchedule(title = "책 읽기", categoryType = "취미"),
                CalendarSchedule(title = "영화 관람", categoryType = "일정"),
                CalendarSchedule(title = "스터디 모임", categoryType = "중요"),
                CalendarSchedule(title = "저녁 식사", categoryType = "일정")
            ),
            "2025-02-12" to listOf(
                CalendarSchedule(title = "회의", categoryType = "중요"),
                CalendarSchedule(title = "운동", categoryType = "취미")
            ),
            "2025-02-13" to listOf(
                CalendarSchedule(title = "회의", categoryType = "일정")
            ),
            "2025-02-14" to listOf(
                CalendarSchedule(title = "출근", categoryType = "일정"),
                CalendarSchedule(title = "이름이 긴 약속", categoryType = "기타")
            )
        )

        CalendarMonth(
            weeks = getMonthDays(YearMonth.now()),
            scheduleMap = sampleScheduleMap
        )
    }
}
