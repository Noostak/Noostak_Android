package com.sopt.presentation.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme

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
                    is GroupSideEffect.NavigateToGroupDetail -> GroupSideEffect.navigateToGroupDetail(
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
                    GroupItem(it, onItemClick(2))
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
