package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.calendar.CalendarMonthPager
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.getLocalDateByPage
import com.sopt.domain.entity.CalendarSchedule

@Composable
fun CalendarMonthScreen(
    pagerState: PagerState,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onItemClick: () -> Unit = {}
) {
    LaunchedEffect(key1 = pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }
            .collect { currentPage ->
                calendarViewModel.getScheduleMonth(getLocalDateByPage(currentPage))
            }
    }

    CalendarMonthPager(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white),
        pagerState = pagerState,
        scheduleMap = scheduleMap,
        onItemClick = { onItemClick() }
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarMonthScreenPreview() {
    NoostakAndroidTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { 10 }
        )

        val scheduleMap = mapOf(
            "2024-02-14" to listOf(
                CalendarSchedule(title = "회의", categoryType = "일정"),
                CalendarSchedule(title = "책 읽기", categoryType = "취미")
            )
        )

        CalendarMonthScreen(
            pagerState = pagerState,
            scheduleMap = scheduleMap,
            modifier = Modifier.fillMaxSize()
        )
    }
}
