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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sopt.core.designsystem.component.button.NoostakFloatingActionButton
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.screen.NoostakEmptyScreen
import com.sopt.core.designsystem.screen.NoostakLoadingScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.showIf
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.domain.entity.GroupEntity
import com.sopt.presentation.R
import com.sopt.presentation.group.component.GroupFloatingActionDialog
import com.sopt.presentation.group.component.GroupItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun GroupRoute(
    paddingValues: PaddingValues,
    groupViewModel: GroupViewModel = hiltViewModel(),
    navigateToGroupDetail: (Long) -> Unit,
    navigateToGroupCreate: () -> Unit,
    navigateToGroupEnter: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val getGroupsState = groupViewModel.getGroupsState.collectAsStateWithLifecycle()

    val showFABDialog by groupViewModel.showFABDialog.collectAsStateWithLifecycle()
    val showErrorDialog by groupViewModel.showErrorDialog.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        groupViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is GroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(sideEffect.groupId)
                    is GroupSideEffect.NavigateToGroupCreate -> navigateToGroupCreate()
                    is GroupSideEffect.NavigateToGroupEnter -> navigateToGroupEnter()
                    is GroupSideEffect.ShowFABDialog -> groupViewModel.showFABDialog(true)
                    is GroupSideEffect.ShowErrorDialog -> groupViewModel.showErrorDialog(true)
                }
            }
    }

    LaunchedEffect(Unit) {
        groupViewModel.getGroups()
    }

    if (showFABDialog) {
        GroupFloatingActionDialog(
            onClick = { groupViewModel.showFABDialog(false) },
            onDismissRequest = { groupViewModel.showFABDialog(false) },
            onCreateGroupClick = {
                groupViewModel.viewModelScope.launch {
                    groupViewModel.navigateToGroupCreate()
                    delay(200)
                    groupViewModel.showFABDialog(false)
                }
            },
            onEnterGroupClick = {
                groupViewModel.viewModelScope.launch {
                    groupViewModel.navigateToGroupEnter()
                    delay(200)
                    groupViewModel.showFABDialog(false)
                }
            }
        )
    }

    if (showErrorDialog) {
        NoostakDialog(
            dialogType = DialogType.NETWORK_FAILURE,
            onClick = {
                groupViewModel.getGroups()
            },
            onDismissRequest = { groupViewModel.showErrorDialog(false) }
        )
    }

    when (getGroupsState.value) {
        is UiState.Loading -> NoostakLoadingScreen()
        is UiState.Success -> {
            GroupScreen(
                paddingValues = paddingValues,
                groupItems = when (val state = getGroupsState.value) {
                    is UiState.Success -> state.data
                    else -> emptyList()
                },
                onItemClick = groupViewModel::navigateToGroupDetail,
                onFabClick = { groupViewModel.showFABDialog(true) },
                showFABDialog = showFABDialog
            )
        }
        is UiState.Failure -> groupViewModel.triggerErrorDialog()

        else -> {}
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun GroupScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groupItems: List<GroupEntity>,
    onItemClick: (Long) -> Unit,
    onFabClick: () -> Unit,
    showFABDialog: Boolean = false
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
            NoostakFloatingActionButton(
                title = stringResource(R.string.fab_group_create),
                modifier = Modifier
                    .offset(x = 0.dp, y = (-22).dp)
                    .showIf(!showFABDialog)
            ) {
                onFabClick()
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (groupItems.isEmpty()) {
            NoostakEmptyScreen(
                emptyText = R.string.text_group_empty_content,
                color = NoostakTheme.colors.gray600,
                style = NoostakTheme.typography.b4Regular
            )
        } else {
            GroupItemScreen(
                innerPadding = innerPadding,
                groupItems = groupItems,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun GroupItemScreen(
    innerPadding: PaddingValues = PaddingValues(),
    groupItems: List<GroupEntity>,
    onItemClick: (Long) -> Unit
) {
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

@Preview(showBackground = true)
@Composable
fun GroupScreenPreview() {
    NoostakAndroidTheme {
        GroupScreen(
            groupItems = listOf(
                GroupEntity(
                    groupId = 1,
                    groupName = "누스탁",
                    groupMemberCount = 15,
                    groupProfileImageUrl = null
                ),
                GroupEntity(
                    groupId = 2,
                    groupName = "유니보이스",
                    groupMemberCount = 16,
                    groupProfileImageUrl = null
                ),
                GroupEntity(
                    groupId = 3,
                    groupName = "솝트",
                    groupMemberCount = 191,
                    groupProfileImageUrl = null
                )
            ),
            onItemClick = {},
            onFabClick = {}
        )
    }
}
