package com.sopt.presentation.group

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButtonWithText
import com.sopt.core.designsystem.component.topappbar.BaseTopAppBar
import com.sopt.core.designsystem.screen.NoostakEmptyScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.GroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.group.component.GroupFloatingActionDialog
import com.sopt.presentation.group.component.GroupItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GroupRoute(
    viewModel: GroupViewModel = hiltViewModel(),
    navigateToGroupDetail: (Long) -> Unit,
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupItems = viewModel.groupItems

    val isEmpty = groupItems.isEmpty()

    val showDialog by viewModel.showDialog.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is GroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(sideEffect.groupId)
                    is GroupSideEffect.NavigateToGroupCreate -> navigateToGroupCreate()
                    is GroupSideEffect.NavigateToGroupEnter -> navigateToGroupEnter()
                }
            }
    }

    if (showDialog) {
        GroupFloatingActionDialog(
            onClick = { viewModel.showLoginDialog(false) },
            onDismissRequest = { viewModel.showLoginDialog(false) },
            onCreateGroupClick = viewModel::navigateToGroupCreate,
            onEnterGroupClick = viewModel::navigateToGroupEnter
        )
    }

    when {
        isEmpty -> NoostakEmptyScreen(
            emptyText = R.string.text_group_empty,
            color = NoostakTheme.colors.gray600,
            style = NoostakTheme.typography.b4Regular
        )

        else -> GroupScreen(
            groupItems = groupItems,
            isFabClicked = viewModel.showDialog,
            onItemClick = viewModel::navigateToGroupDetail,
            onFabClick = { viewModel.showLoginDialog(true) }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun GroupScreen(
    groupItems: List<GroupEntity>,
    isFabClicked: StateFlow<Boolean>,
    onItemClick: (Long) -> Unit,
    onFabClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            BaseTopAppBar(
                title = stringResource(R.string.bottom_nav_group),
                modifier = Modifier,
                isBackButton = false
            )
        },
        floatingActionButton = {
            if (!isFabClicked.value) {
                NoostakFloatingActionButtonWithText(
                    title = stringResource(R.string.fab_group_create),
                    modifier = Modifier.offset(x = 0.dp, y = (-74).dp)
                ) {
                    onFabClick()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                items(items = groupItems, key = { item -> item.groupId }) {
                    GroupItem(it, onItemClick)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = NoostakTheme.colors.gray100,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupScreenPreview() {
    NoostakAndroidTheme {
        GroupScreen(
            groupItems = listOf(
                GroupEntity(groupId = 1, groupName = "누스탁", groupPersonnel = 15, newsImage = null),
                GroupEntity(
                    groupId = 2,
                    groupName = "유니보이스",
                    groupPersonnel = 16,
                    newsImage = null
                ),
                GroupEntity(groupId = 3, groupName = "솝트", groupPersonnel = 191, newsImage = null),
            ),
            isFabClicked = remember { MutableStateFlow(false) },
            onItemClick = {},
            onFabClick = {}
        )
    }
}
