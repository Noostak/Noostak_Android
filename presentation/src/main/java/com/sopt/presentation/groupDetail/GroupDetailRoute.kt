package com.sopt.presentation.groupDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButton
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.showIf
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.domain.entity.CompleteEntity
import com.sopt.domain.entity.GroupDetailEntity
import com.sopt.domain.entity.ProgressEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.screen.CompleteScreen
import com.sopt.presentation.groupDetail.screen.ProgressScreen
import kotlinx.coroutines.launch

@Composable
fun GroupDetailRoute(
    id: Long,
    navigateUp: () -> Unit,
    navigateToCompleteDetail: (Long) -> Unit,
    groupDetailViewModel: GroupDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = groupDetailViewModel.sideEffects) {
        groupDetailViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupDetailSideEffect.NavigateUp -> navigateUp()
                is GroupDetailSideEffect.NavigateToCompleteDetail -> {
                    navigateToCompleteDetail(sideEffect.id)
                }
            }
        }
    }

    GroupDetailScreen(
        id = id,
        tabs = groupDetailViewModel.tabs,
        data = groupDetailViewModel.mockGroupDetail,
        onBackButtonClick = groupDetailViewModel::navigateUp,
        onCompleteClick = groupDetailViewModel::navigateToCompleteDetail
    )
}

@Composable
fun GroupDetailScreen(
    id: Long,
    tabs: List<String>,
    data: GroupDetailEntity,
    onBackButtonClick: () -> Unit,
    onCompleteClick: (Long) -> Unit
) {
    val pagerState = rememberPagerState { tabs.size }
    val topPagerState = rememberPagerState { 2 }
    Scaffold(
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.topbar_group_detail),
                modifier = Modifier,
                isIconVisible = false,
                onBackButtonClick = onBackButtonClick
            )
        },
        floatingActionButton = {
            NoostakFloatingActionButton(
                title = stringResource(R.string.fab_group_detail),
                modifier = Modifier.offset(x = 0.dp, y = (-74).dp)
            ) {
                // TODO: 클릭 이벤트
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_detail),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = data.name,
                        color = NoostakTheme.colors.gray900,
                        style = NoostakTheme.typography.h1Bold
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable { },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    tint = NoostakTheme.colors.gray700
                )
            }
            Row(
                modifier = Modifier.padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tv_group_detail_member, data.memberCount),
                    color = NoostakTheme.colors.gray800,
                    style = NoostakTheme.typography.b2Regular
                )
                Image(
                    modifier = Modifier.padding(start = 5.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_detail_arrow),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(R.string.tv_group_detail_list),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.b1SemiBold
            )
            CustomTabPager(
                pagerState = pagerState,
                tabs = tabs,
                progress = data.progress,
                complete = data.complete,
                onCompleteClick = onCompleteClick
            )
        }
    }
}

@Composable
fun CustomTabPager(
    pagerState: PagerState,
    tabs: List<String>,
    progress: List<ProgressEntity>,
    complete: List<CompleteEntity>,
    onCompleteClick: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = NoostakTheme.colors.gray900,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .clip(CircleShape),
                    height = 4.dp,
                    color = NoostakTheme.colors.gray900
                )
            },
            divider = {
                HorizontalDivider(
                    modifier = Modifier.clip(CircleShape),
                    thickness = 4.dp,
                    color = NoostakTheme.colors.gray200
                )
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    selectedContentColor = NoostakTheme.colors.gray900,
                    unselectedContentColor = NoostakTheme.colors.gray500,
                    interactionSource = NoRippleInteractionSource
                ) {
                    Box {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = title,
                            style = NoostakTheme.typography.b1SemiBold
                        )
                        Box(
                            modifier = Modifier
                                .showIf(pagerState.currentPage == index)
                                .align(Alignment.TopEnd)
                                .offset(x = 8.dp, y = (-2).dp)
                                .clip(CircleShape)
                                .size(8.dp)
                                .background(NoostakTheme.colors.red02)
                        )
                    }
                }
            }
        }
        HorizontalPager(state = pagerState) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (page) {
                    0 -> ProgressScreen(progresses = progress)
                    1 -> CompleteScreen(
                        completes = complete,
                        onItemClicked = { onCompleteClick }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupDetailRoutePreview() {
    val groupDetailViewModel: GroupDetailViewModel = hiltViewModel()
    NoostakAndroidTheme {
        GroupDetailScreen(
            id = 0,
            tabs = groupDetailViewModel.tabs,
            data = groupDetailViewModel.mockGroupDetail,
            onBackButtonClick = {},
            onCompleteClick = {}
        )
    }
}