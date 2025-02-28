package com.sopt.presentation.calendar.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.chip.NoostakCategoryChip
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.entity.ScheduleDetailEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.confirmedDetail.AvailableUserChips
import com.sopt.presentation.groupDetail.confirmedDetail.CompleteDetailInfo
import com.sopt.presentation.groupDetail.confirmedDetail.UnavailableUserChips

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScheduleDetailScreen(
    data: ScheduleDetailEntity,
    onBackBtnClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(63.dp),
            contentAlignment = Alignment.Center
        ) {
            NoostakTopAppBar(
                title = data.name.chunked(10).joinToString("\n"),
                style = NoostakTheme.typography.b1SemiBold,
                isIconVisible = true,
                onBackButtonClick = onBackBtnClick,
                iconResource = R.drawable.ic_back_48,
                paddingHorizontal = 4.dp
            )
        }
        HorizontalDivider(color = NoostakTheme.colors.gray100)
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            CompleteDetailInfo(text = stringResource(R.string.tv_complete_detail_time)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Text(
                        text = data.time,
                        color = NoostakTheme.colors.black,
                        style = NoostakTheme.typography.b4SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            CompleteDetailInfo(text = stringResource(R.string.tv_complete_detail_category)) {
                NoostakCategoryChip(text = data.category)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                CompleteDetailInfo(
                    text = stringResource(
                        R.string.tv_complete_detail_available,
                        data.availableMembers.size
                    )
                )
                FlowRow(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    AvailableUserChips(
                        members = data.availableMembers,
                        myIdentity = IdentityEntity(
                            availability = "available",
                            position = 0,
                            name = "김언지"
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                CompleteDetailInfo(
                    text = stringResource(
                        R.string.tv_complete_detail_unavailable,
                        data.unavailableMembers.size
                    )
                )
                FlowRow(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    UnavailableUserChips(
                        members = data.unavailableMembers,
                        myIdentity = IdentityEntity(
                            availability = "unavailable",
                            position = 0,
                            name = "김언지"
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleDetailScreenPreview() {
    NoostakAndroidTheme {
        ScheduleDetailScreen(
            ScheduleDetailEntity(
                id = 1,
                name = "누스탁 회의djsakfjksadkfdsajfdksajfkajfsdkafdasfa",
                category = "중요",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "김언지", "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("김언지", "박보검", "정해인", "권지용")
            )
        )
    }
}
