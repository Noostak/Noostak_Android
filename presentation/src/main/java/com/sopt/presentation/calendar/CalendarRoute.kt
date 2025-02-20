package com.sopt.presentation.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sopt.core.designsystem.component.bottomsheet.NoostakBottomSheet
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.CalendarFloatingActionDialog
import com.sopt.presentation.calendar.component.CalendarGroup
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleDetailScreen
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit
) {
    val showAddDialog by calendarViewModel.showAddDialog.collectAsStateWithLifecycle()

    val showSheet by calendarViewModel.showSheet.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    LaunchedEffect(key1 = calendarViewModel.sideEffects) {
        calendarViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CalendarSideEffect.NavigateToGroupCreate -> navigateToGroupCreate()
                is CalendarSideEffect.NavigateToGroupEnter -> navigateToGroupEnter()
                is CalendarSideEffect.ShowAddDialog -> {
                    calendarViewModel.showAddDialog(true)
                }
            }
        }
    }

    if (showAddDialog) {
        CalendarFloatingActionDialog(
            onClick = { calendarViewModel.showAddDialog(false) },
            onDismissRequest = { calendarViewModel.showAddDialog(false) },
            onCreateGroupClick = {
                calendarViewModel.navigateToGroupCreate()
                calendarViewModel.showAddDialog(false)
            },
            onEnterGroupClick = {
                calendarViewModel.navigateToGroupEnter()
                calendarViewModel.showAddDialog(false)
            }

        )
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
        groups = calendarViewModel.mockGroups,
        showAddDialog = showAddDialog,
        onAddBtnClick = { calendarViewModel.showAddDialog(true) },
        onBtnClick = { calendarViewModel.showBottomSheet(true) }
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groups: List<CalendarGroupEntity>,
    showAddDialog: Boolean = false,
    onAddBtnClick: () -> Unit = {},
    onBtnClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.appbar_calendar),
                isIconVisible = false,
                isMainAppBar = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = dimensionResource(id = R.dimen.vertical_padding))
        ) {
            CalendarGroup(
                groups = groups,
                showAddDialog = showAddDialog,
                onAddBtnClick = onAddBtnClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onBtnClick() }) {
                Text(text = "Show BottomSheet")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    NoostakAndroidTheme {
        CalendarScreen(
            groups = listOf(
                CalendarGroupEntity(
                    id = 1,
                    groupName = "가응가",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 2,
                    groupName = "먼지 난다",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 3,
                    groupName = "유진면",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 4,
                    groupName = "마늘",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 5,
                    groupName = "누스탁1",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 6,
                    groupName = "누스탁2",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                )
            )
        )
    }
}
