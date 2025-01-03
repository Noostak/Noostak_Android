package com.sopt.presentation.group

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButtonWithText
import com.sopt.core.designsystem.component.topappbar.BaseTopAppBar
import com.sopt.core.designsystem.screen.NoostakEmptyScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.GroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.group.component.GroupFloatingActionButton
import com.sopt.presentation.group.component.GroupItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GroupRoute(
    paddingValues: PaddingValues,
    navigateToGroupDetail: (Long) -> Unit,
    viewModel: GroupViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupItems = viewModel.groupItems

    val isEmpty = groupItems.isEmpty()

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is GroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(
                        sideEffect.groupId
                    )
                }
            }
    }

    when {
        isEmpty -> NoostakEmptyScreen(
            emptyText = R.string.text_group_empty,
            color = NoostakTheme.colors.gray600,
            style = NoostakTheme.typography.b4Regular
        )

        else -> GroupScreen(
            groupItems = groupItems,
            onItemClick = viewModel::navigateToGroupDetail,
            paddingValues = paddingValues,
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GroupScreen(
    groupItems: List<GroupEntity>,
    onItemClick: (Long) -> Unit,
    paddingValues: PaddingValues = PaddingValues(),
) {
    var isFabClicked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .padding(paddingValues),
        topBar = {
            BaseTopAppBar(
                title = stringResource(R.string.bottom_nav_group),
                modifier = Modifier,
                isBackButton = false
            )
        },
        floatingActionButton = {
            if (!isFabClicked) {
                NoostakFloatingActionButtonWithText(
                    title = stringResource(R.string.fab_group_create),
                ) {
                    isFabClicked = true
                }
            } else {
                GroupFloatingActionButton {
                    isFabClicked = false
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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

            if (isFabClicked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { isFabClicked = false }
                )
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
            onItemClick = {}
        )
    }
}
