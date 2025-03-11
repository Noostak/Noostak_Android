package com.sopt.presentation.group

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButton
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
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
    paddingValues: PaddingValues,
    groupViewModel: GroupViewModel = hiltViewModel(),
    navigateToGroupDetail: (Long) -> Unit,
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupItems = groupViewModel.groupItems

    val isEmpty = groupItems.isEmpty()

    val showFABDialog by groupViewModel.showFABDialog.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        groupViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is GroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(sideEffect.groupId)
                    is GroupSideEffect.NavigateToGroupCreate -> navigateToGroupCreate()
                    is GroupSideEffect.NavigateToGroupEnter -> navigateToGroupEnter()
                    is GroupSideEffect.ShowFABDialog -> groupViewModel.showFABDialog(true)
                }
            }
    }

    if (showFABDialog) {
        GroupFloatingActionDialog(
            onClick = { groupViewModel.showFABDialog(false) },
            onDismissRequest = { groupViewModel.showFABDialog(false) },
            onCreateGroupClick = {
                groupViewModel.navigateToGroupCreate()
                groupViewModel.showFABDialog(false)
            },
            onEnterGroupClick = {
                groupViewModel.navigateToGroupEnter()
                groupViewModel.showFABDialog(false)
            }
        )
    }

    when {
        isEmpty -> NoostakEmptyScreen(
            emptyText = R.string.text_group_empty_content,
            color = NoostakTheme.colors.gray600,
            style = NoostakTheme.typography.b4Regular
        )

        else -> GroupScreen(
            paddingValues = paddingValues,
            groupItems = groupItems,
            isFabClicked = groupViewModel.showFABDialog,
            onItemClick = groupViewModel::navigateToGroupDetail,
            onFabClick = { groupViewModel.showFABDialog(true) }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun GroupScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groupItems: List<GroupEntity>,
    isFabClicked: StateFlow<Boolean>,
    onItemClick: (Long) -> Unit,
    onFabClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .padding(paddingValues),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.bottom_nav_group),
                modifier = Modifier,
                isIconVisible = false
            )
        },
        floatingActionButton = {
            if (!isFabClicked.value) {
                NoostakFloatingActionButton(
                    title = stringResource(R.string.fab_group_create),
                    modifier = Modifier.offset(x = 0.dp, y = (-22).dp)
                ) {
                    onFabClick()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
            ) {
                itemsIndexed(items = groupItems, key = { _, item -> item.groupId }) { index, item ->
                    GroupItem(item, onItemClick)
                    if (index != groupItems.lastIndex) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = NoostakTheme.colors.gray100
                        )
                    }
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
                GroupEntity(groupId = 1, groupName = "누스탁", groupMemberCount = 15, groupProfileImageUrl = null),
                GroupEntity(
                    groupId = 2,
                    groupName = "유니보이스",
                    groupMemberCount = 16,
                    groupProfileImageUrl = null
                ),
                GroupEntity(groupId = 3, groupName = "솝트", groupMemberCount = 191, groupProfileImageUrl = null)
            ),
            isFabClicked = remember { MutableStateFlow(false) },
            onItemClick = {},
            onFabClick = {}
        )
    }
}
