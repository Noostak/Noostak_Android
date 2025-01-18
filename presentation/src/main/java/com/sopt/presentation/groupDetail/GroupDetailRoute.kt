package com.sopt.presentation.groupDetail

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButton
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.domain.entity.ConfirmedEntity
import com.sopt.domain.entity.GroupDetailEntity
import com.sopt.domain.entity.ProgressEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.screen.ConfirmedScreen
import com.sopt.presentation.groupDetail.screen.ProgressScreen
import kotlinx.coroutines.launch

@Composable
fun GroupDetailRoute(
    groupId: Long,
    navigateUp: () -> Unit,
    navigateToConfirmedDetail: (Long, Long) -> Unit,
    navigateToGroupMember: (Long) -> Unit,
    navigateToAppointment: (Long, Long, String) -> Unit,
    navigateToAppointmentCreate: (Long) -> Unit,
    groupDetailViewModel: GroupDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = groupDetailViewModel.sideEffects) {
        groupDetailViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupDetailSideEffect.NavigateUp -> navigateUp()
                is GroupDetailSideEffect.NavigateToConfirmedDetail -> {
                    navigateToConfirmedDetail(sideEffect.groupId, sideEffect.confirmedId)
                }

                is GroupDetailSideEffect.NavigateToGroupMember -> {
                    navigateToGroupMember(sideEffect.groupId)
                }

                is GroupDetailSideEffect.NavigateToAppointment -> {
                    navigateToAppointment(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.appointmentName
                    )
                }

                is GroupDetailSideEffect.NavigateToAppointmentCreate -> {
                    navigateToAppointmentCreate(sideEffect.groupId)
                }
            }
        }
    }

    GroupDetailScreen(
        groupId = groupId,
        tabs = groupDetailViewModel.tabs,
        data = groupDetailViewModel.mockGroupDetail,
        onBackButtonClick = groupDetailViewModel::navigateUp,
        onConfirmedClick = groupDetailViewModel::navigateToConfirmedDetail,
        onGroupMemberClick = groupDetailViewModel::navigateToGroupMember,
        onProgressClick = groupDetailViewModel::navigateToAppointment,
        onAppointmentCreateClick = groupDetailViewModel::navigateToAppointmentCreate
    )
}

@Composable
fun GroupDetailScreen(
    groupId: Long,
    tabs: List<String>,
    data: GroupDetailEntity,
    onBackButtonClick: () -> Unit,
    onConfirmedClick: (Long, Long) -> Unit,
    onGroupMemberClick: (Long) -> Unit,
    onProgressClick: (Long, Long, String) -> Unit,
    onAppointmentCreateClick: (Long) -> Unit
) {
    val pagerState = rememberPagerState { tabs.size }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.appbar_group_detail),
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
                onAppointmentCreateClick(groupId)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
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
                        text = data.groupName,
                        color = NoostakTheme.colors.gray900,
                        style = NoostakTheme.typography.h1Bold
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            // 공유 기능
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "공유하고자 하는 그룹 아이디: $groupId"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            ContextCompat.startActivity(context, shareIntent, null)
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    tint = NoostakTheme.colors.gray700
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .noRippleClickable { onGroupMemberClick(groupId) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tv_group_detail_member, data.groupMembersCount),
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
                groupId = groupId,
                pagerState = pagerState,
                tabs = tabs,
                progressEntities = data.progressEntities,
                confirmedEntities = data.confirmedEntities,
                onProgressClick = onProgressClick,
                onConfirmedClick = onConfirmedClick
            )
        }
    }
}

@Composable
fun CustomTabPager(
    groupId: Long,
    pagerState: PagerState,
    tabs: List<String>,
    progressEntities: List<ProgressEntity>,
    confirmedEntities: List<ConfirmedEntity>,
    onProgressClick: (Long, Long, String) -> Unit,
    onConfirmedClick: (Long, Long) -> Unit
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
            }
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
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = title,
                        style = NoostakTheme.typography.b1SemiBold
                    )
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
                    0 -> ProgressScreen(
                        groupId = groupId,
                        progressEntities = progressEntities,
                        onItemClicked = { groupId, appointmentsId, appointmentName ->
                            onProgressClick(groupId, appointmentsId, appointmentName)
                        }
                    )

                    1 -> ConfirmedScreen(
                        groupId = groupId,
                        confirmedEntities = confirmedEntities,
                        onItemClicked = { groupId, confirmedId ->
                            onConfirmedClick(groupId, confirmedId)
                        }
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
            groupId = 0,
            tabs = groupDetailViewModel.tabs,
            data = groupDetailViewModel.mockGroupDetail,
            onBackButtonClick = {},
            onConfirmedClick = { _, _ -> },
            onGroupMemberClick = {},
            onProgressClick = { _, _, _ -> },
            onAppointmentCreateClick = {}
        )
    }
}
