package com.sopt.presentation.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.appointment.scrollToItem
import com.sopt.presentation.calendar.component.CalendarGroupItem

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
    val density = LocalDensity.current
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
                Image(
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.CenterEnd)
                        .zIndex(2f),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_add),
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .padding(end = 26.dp)
                        .size(width = 64.dp, height = 94.dp)
                        .align(Alignment.CenterEnd)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    NoostakTheme.colors.white
                                )
                            ),
                            shape = RectangleShape
                        )
                )
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end = 26.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(items = groups, key = { _, item -> item.id }) { index, group ->
                        CalendarGroupItem(
                            data = group,
                            isSelected = index == selectedGroup,
                            onClick = {
                                selectedGroup = index
                                scrollToItem(listState, coroutineScope, density, index)
                            }
                        )
                        if (index == groups.lastIndex) {
                            Spacer(
                                modifier = Modifier.width(41.dp)
                            )
                        }
                    }
                }
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
                    groupName = "유잔면",
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
