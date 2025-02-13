package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.CalendarMonth
import com.sopt.presentation.calendar.component.WeekDaysHeader
import com.sopt.presentation.calendar.component.YearMonthHeader
import com.sopt.presentation.calendar.model.MonthModel
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

    CalendarScreen(
        paddingValues = paddingValues,
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
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
        val monthModel = MonthModel(YearMonth.now())

        YearMonthHeader(
            date = YearMonth.now(),
        )
        WeekDaysHeader()
        CalendarMonth(
            modifier = Modifier.padding(bottom = 34.dp),
            weeks = monthModel.calendarMonth,
        )
    }
}

@Preview
@Composable
fun CalendarScreenPreview() {
    NoostakAndroidTheme {
        CalendarScreen()
    }
}
