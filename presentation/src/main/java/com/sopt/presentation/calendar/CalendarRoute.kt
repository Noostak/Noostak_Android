package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sopt.core.designsystem.component.bottomsheet.NoostakBottomSheet
import com.sopt.core.designsystem.component.calendar.WeekDaysHeader
import com.sopt.core.designsystem.component.calendar.YearMonthHeader
import com.sopt.core.designsystem.component.topappbar.NoostakLogoAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.getYearMonthByPage
import com.sopt.core.extension.initialPage
import com.sopt.core.extension.pageCount
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.CalendarFloatingActionDialog
import com.sopt.presentation.calendar.component.CalendarGroup
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleDetailScreen
import com.sopt.presentation.calendar.component.bottomsheet.ScheduleListScreen
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit,
    navigateToAppointmentCreate: (Long) -> Unit
) {
    val showAddDialog by calendarViewModel.showAddDialog.collectAsStateWithLifecycle()

    val showBottomSheet by calendarViewModel.showBottomSheet.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    val scheduleMap by calendarViewModel.scheduleMap.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pageCount }
    )

    val currentYearMonth by remember { derivedStateOf { getYearMonthByPage(pagerState.currentPage) } }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                calendarViewModel.getScheduleMonth(getYearMonthByPage(page).atDay(1))
            }
    }

    LaunchedEffect(key1 = calendarViewModel.sideEffects) {
        calendarViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CalendarSideEffect.NavigateToGroupCreate -> navigateToGroupCreate()
                is CalendarSideEffect.NavigateToGroupEnter -> navigateToGroupEnter()
                is CalendarSideEffect.NavigateToAppointmentCreate -> navigateToAppointmentCreate(
                    calendarViewModel.mockScheduleList.groupId
                )

                is CalendarSideEffect.ShowAddDialog -> calendarViewModel.showAddDialog(true)
                is CalendarSideEffect.ShowBottomSheet -> calendarViewModel.showBottomSheet(true)
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

    if (showBottomSheet) {
        NoostakBottomSheet(
            onDismissRequest = {
                calendarViewModel.showBottomSheet(false)
                navController.popBackStack(SCHEDULE_LIST, inclusive = false)
            },
            content = {
                NavHost(navController, startDestination = SCHEDULE_LIST) {
                    composable(SCHEDULE_LIST) { backStackEntry ->
                        ScheduleListScreen(
                            data = calendarViewModel.mockScheduleList,
                            onItemClick = { schedule ->
                                backStackEntry.savedStateHandle[SCHEDULE] =
                                    schedule.scheduleId // 바꿔야 함
                                navController.navigate(SCHEDULE_DETAIL)
                            },
                            onCreateAppointmentBtnClick = {
                                calendarViewModel.navigateToAppointmentCreate()
                                calendarViewModel.showBottomSheet(false)
                            }
                        )
                    }
                    composable(SCHEDULE_DETAIL) {
                        val schedule =
                            navController.previousBackStackEntry?.savedStateHandle?.get<Long>(
                                SCHEDULE
                            )
                        schedule?.let { id ->
                            ScheduleDetailScreen(
                                data = calendarViewModel.mockScheduleDetail,
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
        scheduleMap = scheduleMap,
        pagerState = pagerState,
        currentYearMonth = currentYearMonth,
        showAddDialog = showAddDialog,
        onAddBtnClick = { calendarViewModel.showAddDialog(true) },
        onItemClick = { calendarViewModel.showBottomSheet(true) } // 바꿔야 함
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groups: List<CalendarGroupEntity>,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    pagerState: PagerState,
    currentYearMonth: YearMonth,
    showAddDialog: Boolean = false,
    onAddBtnClick: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakLogoAppBar()
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
            Spacer(modifier = Modifier.height(24.dp))
            CalendarContent(
                scheduleMap = scheduleMap,
                pagerState = pagerState,
                currentYearMonth = currentYearMonth,
                onItemClick = { onItemClick() }
            )
        }
    }
}

@Composable
private fun CalendarContent(
    scheduleMap: Map<String, List<CalendarSchedule>>,
    pagerState: PagerState,
    currentYearMonth: YearMonth,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
            .padding(bottom = 34.dp)
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YearMonthHeader(date = currentYearMonth)
        Spacer(modifier = Modifier.height(8.dp))
        WeekDaysHeader()
        CalendarMonthScreen(
            pagerState = pagerState,
            scheduleMap = scheduleMap,
            onItemClick = { onItemClick() }
        )
    }
}

const val SCHEDULE = "schedule"
const val SCHEDULE_LIST = "schedule_list"
const val SCHEDULE_DETAIL = "schedule_detail"

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    NoostakAndroidTheme {
        val pagerState = rememberPagerState(
            initialPage = initialPage,
            pageCount = { pageCount }
        )

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
            ),
            scheduleMap = emptyMap(),
            pagerState = pagerState,
            currentYearMonth = getYearMonthByPage(initialPage)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenEmptyPreview() {
    NoostakAndroidTheme {
        CalendarScreen(
            groups = emptyList(),
            scheduleMap = emptyMap(),
            pagerState = rememberPagerState(
                initialPage = initialPage,
                pageCount = { pageCount }
            ),
            currentYearMonth = getYearMonthByPage(initialPage)
        )
    }
}
