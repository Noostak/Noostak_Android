package com.sopt.presentation.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.presentation.calendar.viewmodel.CalendarViewModel

@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    navigateToInfoScreen: () -> Unit,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarViewModel.sideEffects) {
        calendarViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                CalendarSideEffect.NavigateToInfo -> navigateToInfoScreen()
            }
        }
    }

    CalendarScreen(
        paddingValues = paddingValues,
        onNavigateToCalendarInfoScreen = calendarViewModel::navigateToCalendarInfoScreen,
    )

}


@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateToCalendarInfoScreen: () -> Unit,
) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onNavigateToCalendarInfoScreen() }) {
            Text("InfoScreen ㄱㄱ")
        }
    }
}
