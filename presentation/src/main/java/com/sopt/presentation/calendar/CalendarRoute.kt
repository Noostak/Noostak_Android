package com.sopt.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.calendar.component.CalendarGroupItem
import kotlinx.coroutines.launch

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
        groups = calendarViewModel.mockGroups
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groups: List<CalendarGroupEntity>
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var selectedGroup by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = "캘린더",
                isIconVisible = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.default_padding))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            shape = CircleShape,
                            color = NoostakTheme.colors.gray900
                        )
                        .size(46.dp)
                        .align(Alignment.CenterEnd)
                        .zIndex(1f)
                )
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentPadding = PaddingValues(end = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(items = groups, key = { _, item -> item.id }) { index, group ->
                        CalendarGroupItem(
                            data = group,
                            isSelected = index == selectedGroup,
                            onClick = {
                                selectedGroup = index
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
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
                groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
            ),
            CalendarGroupEntity(
                id = 3,
                groupName = "유잔면",
                groupImage = "https://avatars.githubusercontent.com/u/68536115?s=96&v=4"
            ),
            CalendarGroupEntity(
                id = 4,
                groupName = "마늘",
                groupImage = "https://avatars.githubusercontent.com/u/79982452?s=96&v=4"
            ),
        )
    )
}
