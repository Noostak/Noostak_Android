package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.calendar.component.CalendarMonthPager
import com.sopt.presentation.calendar.model.CalendarModel

@Composable
fun CalendarMonthScreen(
    calendarModel: CalendarModel,
    pagerState: PagerState,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { currentPage ->
                val localDate = calendarModel.getLocalDateByPage(currentPage)
                viewModel.getScheduleMonth(localDate)
            }
    }

    CalendarMonthPager(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white),
        pagerState = pagerState,
        calendarModel = calendarModel,
        scheduleMap = scheduleMap
    )
}
