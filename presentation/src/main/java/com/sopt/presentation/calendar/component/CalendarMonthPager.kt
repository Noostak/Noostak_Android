package com.sopt.presentation.calendar.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.calendar.model.CalendarModel

@Composable
internal fun CalendarMonthPager(
    pagerState: PagerState,
    calendarModel: CalendarModel,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        val monthModel = calendarModel.getMonthModelByPage(page)

        CalendarMonth(
            weeks = monthModel.calendarMonth,
            scheduleMap = scheduleMap,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun CalendarMonthScreenPreview() {
    NoostakAndroidTheme {
        val calendarModel = CalendarModel()
        val pagerState = rememberPagerState(
            initialPage = calendarModel.initialPage,
            pageCount = { calendarModel.pageCount }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val monthModel = calendarModel.getMonthModelByPage(page = page)

            CalendarMonth(
                weeks = monthModel.calendarMonth,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
