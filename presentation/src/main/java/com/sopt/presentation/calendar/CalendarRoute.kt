package com.sopt.presentation.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sopt.core.designsystem.component.bottomsheet.NoostakBottomSheet
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleDetailScreen
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val showSheet by calendarViewModel.showSheet.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    LaunchedEffect(key1 = calendarViewModel.sideEffects) {
        calendarViewModel.sideEffects.collect { sideEffect ->
        }
    }

    if (showSheet) {
        NoostakBottomSheet(
            onDismissRequest = {
                calendarViewModel.showBottomSheet(false)
                navController.popBackStack("first", inclusive = false)
            },
            content = {
                NavHost(navController, startDestination = "first") {
                    composable("first") {
                        ScheduleListScreen(
                            data = calendarViewModel.mockSchedule,
                            onItemClick = { schedule ->
//                                navController.currentBackStackEntry?.savedStateHandle?.set(
//                                    "schedule",
//                                    schedule
//                                )

                                calendarViewModel.updateDetailSchedule(schedule)
                                navController.navigate("second")
                            },
                            onConfirmBtnClick = { calendarViewModel.showBottomSheet(false) }
                        )
                    }
                    composable("second") { backStackEntry ->
//                        val schedule = backStackEntry.savedStateHandle.get<ScheduleDetailEntity>("schedule")

                        val schedule =
                            calendarViewModel.detailSchedule.collectAsStateWithLifecycle().value

                        schedule?.let {
                            ScheduleDetailScreen(
                                data = it,
                                onBackBtnClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        )
    }

    CalendarScreen(
        paddingValues = paddingValues,
        onBtnClick = { calendarViewModel.showBottomSheet(true) }
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    onBtnClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onBtnClick() }) {
            Text(text = "Show BottomSheet")
        }
    }
}
