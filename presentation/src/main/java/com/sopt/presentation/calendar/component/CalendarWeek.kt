package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.sopt.core.extension.toDateString
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.domain.entity.DayEntity
import com.sopt.presentation.calendar.component.next.CalendarScheduleGroup
import java.time.LocalDate

@Composable
internal fun CalendarWeek(
    dayInfo: List<DayEntity>,
    modifier: Modifier = Modifier,
    scheduleMap: Map<String, List<CalendarSchedule>> = emptyMap()
) {
    Row(
        modifier = modifier.background(NoostakTheme.colors.white),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        dayInfo.forEach { day ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val schedules = scheduleMap[day.day.toDateString()]?.takeIf { it.isNotEmpty() }

                CalendarDay(
                    dayInfo = day,
                    isToday = day.day.isToday(),
                )
                Spacer(modifier = Modifier.height(2.dp))

                if (!day.isOtherMonth && schedules != null) {
                    CalendarScheduleGroup(
                        scheduleList = schedules,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarWeekGroupNoScrapPreview() {
    NoostakAndroidTheme {
        val sampleScheduleMap = mapOf(
            LocalDate.now().toDateString() to listOf(
                CalendarSchedule(title = "회의", color = "#A9DBBE"),
                CalendarSchedule(title = "운동", color = "#A9DBBE"),
                CalendarSchedule(title = "저녁식사", color = "#8D78D8"),
                CalendarSchedule(title = "출근", color = "#8D78D8")
            ),
            LocalDate.now().minusDays(2).toDateString() to listOf(
                CalendarSchedule(title = "친구 만남", color = "#3E8EFF")
            )
        )

        CalendarWeek(
            dayInfo = listOf(
                DayEntity(day = LocalDate.now().minusDays(3), isOtherMonth = false),
                DayEntity(day = LocalDate.now().minusDays(2), isOtherMonth = false),
                DayEntity(day = LocalDate.now().minusDays(1), isOtherMonth = false),
                DayEntity(day = LocalDate.now(), isOtherMonth = false),
                DayEntity(day = LocalDate.now().plusDays(1), isOtherMonth = false),
                DayEntity(day = LocalDate.now().plusDays(2), isOtherMonth = false),
                DayEntity(day = LocalDate.now().plusDays(3), isOtherMonth = false)
            ),
            scheduleMap = sampleScheduleMap,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}
