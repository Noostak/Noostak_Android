package com.sopt.presentation.groupDetail.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.chip.NoostakCategoryChip
import com.sopt.core.designsystem.component.chip.NoostakUserChip
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.CompleteEntity
import com.sopt.presentation.groupDetail.GroupDetailSideEffect
import com.sopt.presentation.groupDetail.GroupDetailViewModel

@Composable
fun CompleteDetailRoute(
    id: Long,
    navigateUp: () -> Unit,
    groupDetailViewModel: GroupDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = groupDetailViewModel.sideEffects) {
        groupDetailViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupDetailSideEffect.NavigateUp -> navigateUp()
                is GroupDetailSideEffect.NavigateToCompleteDetail -> navigateUp()
            }
        }

    }
    CompleteDetailScreen(
        data = groupDetailViewModel.mockGroupDetail.complete[id.toInt()],
        onBackButtonClick = groupDetailViewModel::navigateUp
    )
}

@Composable
fun CompleteDetailScreen(
    data: CompleteEntity,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            NoostakTopAppBar(
                title = data.title,
                modifier = Modifier,
                isIconVisible = true,
                onBackButtonClick = { onBackButtonClick() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "약속 정보",
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.t4Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(20.dp),
                        color = NoostakTheme.colors.gray200
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CompleteDetailInfo(text = "약속 시간") {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            text = "9/5",
                            color = NoostakTheme.colors.black,
                            style = NoostakTheme.typography.b4SemiBold
                        )
                        Text(
                            text = "10:00",
                            color = NoostakTheme.colors.black,
                            style = NoostakTheme.typography.b4SemiBold
                        )
                    }
                }
                CompleteDetailInfo(text = "약속 유형") {
                    NoostakCategoryChip(text = "중요", backgroundColor = NoostakTheme.colors.orange)
                }
                Column {
                    CompleteDetailInfo(text = "가능한 친구 1")
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.padding(top = 10.dp),
                        columns = StaggeredGridCells.Fixed(6),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalItemSpacing = 8.dp
                    ) {
                        items(12) {
                            NoostakUserChip(
                                text = "이가을",
                                textColor = if (it == 0) NoostakTheme.colors.black else NoostakTheme.colors.gray800,
                                backgroundColor = if (it == 0) NoostakTheme.colors.blue200 else NoostakTheme.colors.white,
                                borderColor = NoostakTheme.colors.blue200
                            )
                        }
                    }
                }
                Column {
                    CompleteDetailInfo(text = "불가능한 친구 1")
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.padding(top = 10.dp),
                        columns = StaggeredGridCells.Fixed(6),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalItemSpacing = 8.dp
                    ) {
                        items(12) {
                            NoostakUserChip(
                                text = "이가을",
                                textColor = NoostakTheme.colors.gray800,
                                backgroundColor = NoostakTheme.colors.gray200,
                                borderColor = NoostakTheme.colors.gray200
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompleteDetailInfo(
    text: String,
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.c3Regular
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteDetailScreen() {
    NoostakAndroidTheme {
        CompleteDetailScreen(
            data = CompleteEntity(
                id = 0,
                title = "3차 회의",
                date = "Date"
            ),
            onBackButtonClick = {}
        )
    }
}