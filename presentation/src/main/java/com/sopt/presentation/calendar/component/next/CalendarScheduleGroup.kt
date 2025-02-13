package com.sopt.presentation.calendar.component.next

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.calendar.component.ScheduleItem
import kotlin.math.max
import kotlin.math.min

@Composable
internal fun CalendarScheduleGroup(
    scheduleList: List<CalendarSchedule>,
    modifier: Modifier = Modifier,
) {
    val displayCount = min(scheduleList.size, MAX_VISIBLE_SCHEDULES)
    val hiddenCount = max(0, scheduleList.size - displayCount)

    Column(modifier = modifier.fillMaxWidth()) {
        scheduleList.take(displayCount).forEach { schedule ->
            ScheduleItem(
                title = schedule.title,
                color = schedule.color.toColor()
            )
        }

        if (hiddenCount > 0) {
            Text(
                text = "+$hiddenCount",
                style = NoostakTheme.typography.c5Regular,
                color = NoostakTheme.colors.gray800,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}

private const val MAX_VISIBLE_SCHEDULES = 4

fun String.toColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScheduleGroupPreview() {
    NoostakAndroidTheme {
        Box(
            modifier = Modifier.background(NoostakTheme.colors.white)
        ) {
            CalendarScheduleGroup(
                scheduleList = listOf(
                    CalendarSchedule(
                        scrapId = 1,
                        title = "Meeting",
                        color = "#8D78D8"
                    ),
                    CalendarSchedule(
                        scrapId = 2,
                        title = "Workout",
                        color = "#3E8EFF",
                    ),
                    CalendarSchedule(
                        scrapId = 3,
                        title = "Dinner",
                        color = "#3E8EFF"
                    ),
                    CalendarSchedule(
                        scrapId = 4,
                        title = "Meeting",
                        color = "#3E8EFF"
                    ),
                    CalendarSchedule(
                        scrapId = 5,
                        title = "Workout",
                        color = "#3E8EFF"
                    )
                )
            )
        }
    }
}
