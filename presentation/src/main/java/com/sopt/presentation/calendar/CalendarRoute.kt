package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.WeekDaysHeader
import com.sopt.presentation.calendar.component.YearMonthHeader
import com.sopt.presentation.calendar.model.CalendarModel
import java.time.YearMonth

@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarViewModel.sideEffects) {
        calendarViewModel.sideEffects.collect { sideEffect ->
        }
    }

    val calendarModel = remember { CalendarModel(startYear = 2020, endYear = 2030) }
    val pagerState = rememberPagerState(
        initialPage = calendarModel.initialPage,
        pageCount = { calendarModel.pageCount }
    )

    CalendarScreen(
        paddingValues = paddingValues,
        calendarViewModel = calendarViewModel,
        calendarModel = calendarModel,
        pagerState = pagerState,
        modifier = Modifier.background(NoostakTheme.colors.white)
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    calendarViewModel: CalendarViewModel,
    calendarModel: CalendarModel,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scheduleMap by calendarViewModel.scheduleMap.collectAsState()

    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentYearMonth = calendarModel.getMonthModelByPage(page).yearMonth
            }
    }

    CalendarContent(
        paddingValues = paddingValues,
        scheduleMap = scheduleMap,
        calendarModel = calendarModel,
        pagerState = pagerState,
        currentYearMonth = currentYearMonth,
        modifier = modifier
    )
}

@Composable
private fun CalendarContent(
    paddingValues: PaddingValues,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    calendarModel: CalendarModel,
    pagerState: PagerState,
    currentYearMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
            .padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YearMonthHeader(date = currentYearMonth)
        WeekDaysHeader()
        CalendarMonthScreen(
            calendarModel = calendarModel,
            pagerState = pagerState,
            scheduleMap = scheduleMap,
            viewModel = hiltViewModel()
        )
    }
}

@Preview
@Composable
fun CalendarScreenPreview() {
    NoostakAndroidTheme {
        val calendarModel = CalendarModel(startYear = 2020, endYear = 2030)
        val pagerState = rememberPagerState(
            initialPage = calendarModel.initialPage,
            pageCount = { calendarModel.pageCount }
        )

        val initialYearMonth =
            calendarModel.getMonthModelByPage(calendarModel.initialPage).yearMonth

        CalendarContent(
            paddingValues = PaddingValues(),
            scheduleMap = mapOf(
                "2025-02-04" to listOf(
                    CalendarSchedule(title = "회의", categoryType = "중요"),
                    CalendarSchedule(title = "책 읽기", categoryType = "취미")
                ),
                "2025-02-12" to listOf(
                    CalendarSchedule(title = "운동", categoryType = "취미")
                )
            ),
            calendarModel = calendarModel,
            pagerState = pagerState,
            currentYearMonth = initialYearMonth
        )
    }
}
