package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.extension.getMonthDays
import com.sopt.core.extension.getYearMonthByPage
import com.sopt.core.extension.initialPage
import com.sopt.core.extension.pageCount
import com.sopt.domain.entity.CalendarSchedule
import java.time.LocalDate

@Composable
fun CalendarMonthPager(
    pagerState: PagerState,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    modifier: Modifier = Modifier,
    onItemClick: (LocalDate) -> Unit = {}
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        CalendarMonth(
            weeks = getMonthDays(getYearMonthByPage(page)),
            scheduleMap = scheduleMap,
            modifier = Modifier.fillMaxSize(),
            onItemClick = onItemClick
        )
    }
}

@Preview
@Composable
private fun CalendarMonthScreenPreview() {
    NoostakAndroidTheme {
        val pagerState = rememberPagerState(
            initialPage = initialPage,
            pageCount = { pageCount }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            CalendarMonth(
                weeks = getMonthDays(getYearMonthByPage(page)),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
