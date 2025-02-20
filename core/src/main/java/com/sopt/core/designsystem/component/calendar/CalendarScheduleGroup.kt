package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CategoryType
import com.sopt.domain.entity.CalendarSchedule
import kotlin.math.min

@Composable
fun CalendarScheduleGroup(
    scheduleList: List<CalendarSchedule>,
    isPastDate: Boolean,
    modifier: Modifier = Modifier
) {
    val displayCount = min(scheduleList.size, MAX_VISIBLE_SCHEDULES)
    val hiddenCount = scheduleList.size - displayCount

    Column(
        modifier = modifier
            .wrapContentSize()
            .graphicsLayer(alpha = if (isPastDate) 0.6f else 1.0f)
    ) {
        scheduleList.take(displayCount).forEach { schedule ->
            ScheduleItem(
                title = schedule.title,
                categoryType = CategoryType.fromText(LocalContext.current, schedule.categoryType)
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

private const val MAX_VISIBLE_SCHEDULES = 3

@Preview
@Composable
fun CalendarSchedulePastPreview() {
    NoostakAndroidTheme {
        Box(
            modifier = Modifier.background(NoostakTheme.colors.white)
        ) {
            CalendarScheduleGroup(
                scheduleList = listOf(
                    CalendarSchedule(
                        scrapId = 1,
                        title = "Meeting",
                        categoryType = "취미"
                    ),
                    CalendarSchedule(
                        scrapId = 2,
                        title = "Workout",
                        categoryType = "일정"
                    )
                ),
                isPastDate = true
            )
        }
    }
}

@Preview
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
                        categoryType = "취미"
                    ),
                    CalendarSchedule(
                        scrapId = 2,
                        title = "Workout",
                        categoryType = "일정"
                    ),
                    CalendarSchedule(
                        scrapId = 3,
                        title = "Dinner",
                        categoryType = "일정"
                    ),
                    CalendarSchedule(
                        scrapId = 4,
                        title = "Meeting",
                        categoryType = "중요"
                    ),
                    CalendarSchedule(
                        scrapId = 5,
                        title = "Workout",
                        categoryType = "기타"
                    )
                ),
                isPastDate = false
            )
        }
    }
}
