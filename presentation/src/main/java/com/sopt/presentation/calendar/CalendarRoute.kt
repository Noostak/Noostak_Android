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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.CalendarFloatingActionDialog
import com.sopt.presentation.calendar.component.CalendarGroup
import com.sopt.presentation.calendar.component.WeekDaysHeader
import com.sopt.presentation.calendar.component.YearMonthHeader
import com.sopt.presentation.calendar.model.CalendarModel
import java.time.YearMonth

@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit
) {
    val showAddDialog by calendarViewModel.showAddDialog.collectAsStateWithLifecycle()

    val scheduleMap by calendarViewModel.scheduleMap.collectAsStateWithLifecycle()

    val calendarModel = remember { CalendarModel(startYear = 2020, endYear = 2030) }
    val pagerState = rememberPagerState(
        initialPage = calendarModel.initialPage,
        pageCount = { calendarModel.pageCount }
    )

    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentYearMonth = calendarModel.getMonthModelByPage(page).yearMonth
            }
    }

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

    CalendarScreen(
        paddingValues = paddingValues,
        groups = calendarViewModel.mockGroups,
        scheduleMap = scheduleMap,
        calendarModel = calendarModel,
        pagerState = pagerState,
        currentYearMonth = currentYearMonth,
        showAddDialog = showAddDialog,
        onAddBtnClick = { calendarViewModel.showAddDialog(true) }
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groups: List<CalendarGroupEntity>,
    scheduleMap: Map<String, List<CalendarSchedule>>,
    calendarModel: CalendarModel,
    pagerState: PagerState,
    currentYearMonth: YearMonth,
    showAddDialog: Boolean = false,
    onAddBtnClick: () -> Unit = {}
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
            Spacer(modifier = Modifier.height(24.dp))
            CalendarContent(
                scheduleMap = scheduleMap,
                calendarModel = calendarModel,
                pagerState = pagerState,
                currentYearMonth = currentYearMonth,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun CalendarContent(
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
            .padding(bottom = 34.dp)
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YearMonthHeader(date = currentYearMonth)
        Spacer(modifier = Modifier.height(8.dp))
        WeekDaysHeader()
        CalendarMonthScreen(
            calendarModel = calendarModel,
            pagerState = pagerState,
            scheduleMap = scheduleMap,
            viewModel = hiltViewModel()
        )
    }
}

@Preview(showBackground = true)
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
            calendarModel = calendarModel,
            pagerState = pagerState,
            currentYearMonth = initialYearMonth
        )
    }
}
