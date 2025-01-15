package com.sopt.presentation.calendar.calendarPeriod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.calendar.NoostakCalendar
import com.sopt.core.designsystem.component.progressbar.NoostakProgressBar
import com.sopt.core.designsystem.component.text.NoostakHeaderText
import com.sopt.core.designsystem.component.toggle.NoostakSwitch
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun CalendarPeriodRoute(
    appointmentName: String,
    category: String,
    time: Int,
    navigateToTimePicker: (String, String, Int, Boolean, List<String>) -> Unit,
    calendarPeriodViewModel: CalendarPeriodViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarPeriodViewModel.sideEffects) {
        calendarPeriodViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CalendarPeriodSideEffect.NavigateToTimePicker -> {
                    navigateToTimePicker(
                        sideEffect.appointmentName,
                        sideEffect.category,
                        sideEffect.time,
                        sideEffect.isSingleDateMode,
                        sideEffect.dates
                    )
                }
            }
        }
    }

    CalendarPeriodScreen(
        onButtonClick = calendarPeriodViewModel::navigateToCalendarTimePicker,
        appointmentName = appointmentName,
        category = category,
        time = time,
        days = calendarPeriodViewModel.days
    )
}

@Composable
fun CalendarPeriodScreen(
    onButtonClick: (String, String, Int, Boolean, List<String>) -> Unit,
    appointmentName: String,
    category: String,
    time: Int,
    days: List<String>
) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var dates by remember { mutableStateOf(listOf<String>()) }
    var isSingleDateMode by remember { mutableStateOf(false) }

    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.text_calendar_appointment),
                isIconVisible = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.horizontal_padding))
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            NoostakProgressBar(progressBar = listOf(false, true, false))

            NoostakHeaderText(text = stringResource(R.string.text_calendar_appointment_choose))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_calendar_appointment_one_day),
                    style = typography.b2Regular,
                    textAlign = TextAlign.Start,
                    color = colors.gray900
                )
                NoostakSwitch(
                    checked = isSingleDateMode,
                    onCheckedChange = { isSingleDateMode = it }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.gray200)
                    .padding(bottom = 17.dp)
            )

            NoostakCalendar(
                start = startDate,
                end = endDate,
                isSingleDate = isSingleDateMode,
                isRangeSelected = { selectedDates ->
                    if (isSingleDateMode) {
                        dates = selectedDates
                        startDate = ""
                        endDate = ""
                    } else {
                        dates = selectedDates
                        if (selectedDates.isNotEmpty()) {
                            startDate = selectedDates.first()
                            endDate = selectedDates.last()
                        }
                    }
                },
                days = days
            )

            Spacer(modifier = Modifier.weight(1f))

            NoostakBottomButton(
                text = stringResource(R.string.text_calendar_appointment_next),
                onButtonClick = {
                    onButtonClick(appointmentName, category, time, isSingleDateMode, dates)
                },
                isEnabled = dates.isNotEmpty(),
                deactivateColor = NoostakTheme.colors.gray500,
                activateColor = NoostakTheme.colors.gray900,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
