package com.sopt.presentation.groupDetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.CalculateTime
import com.sopt.domain.entity.ConfirmedEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.GroupDetailViewModel

@Composable
fun ConfirmedScreen(
    groupId: Long,
    confirmedEntities: List<ConfirmedEntity>,
    onItemClicked: (Long, Long, String) -> Unit
) {
    if (confirmedEntities.isEmpty()) {
        Text(
            modifier = Modifier.padding(top = 103.dp),
            text = stringResource(R.string.tv_group_detail_no_complete),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b2Regular
        )
    } else {
        LazyColumn {
            items(confirmedEntities, key = { it.appointmentId }) {
                ConfirmedItem(
                    groupId = groupId,
                    confirmedEntity = it,
                    onItemClicked = onItemClicked
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = NoostakTheme.colors.gray200
                )
            }
        }
    }
}

@Composable
fun ConfirmedItem(
    groupId: Long,
    confirmedEntity: ConfirmedEntity,
    onItemClicked: (Long, Long, String) -> Unit
) {
    val calculateTime = CalculateTime()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onItemClicked(groupId, confirmedEntity.appointmentId, confirmedEntity.appointmentName) }
            .padding(
                top = 15.dp,
                bottom = 16.dp,
                start = 2.dp,
                end = 0.dp
            )
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(
                        top = 5.dp,
                        start = 4.dp,
                        bottom = 5.dp,
                        end = 3.dp
                    )
                    .background(
                        color = NoostakTheme.colors.blue,
                        shape = CircleShape
                    )
                    .size(13.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = confirmedEntity.appointmentName,
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.b1SemiBold,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp),
                    text = calculateTime.extractFullDate(confirmedEntity.date),
                    color = NoostakTheme.colors.gray700,
                    style = NoostakTheme.typography.c3Regular,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompleteScreenPreview() {
    NoostakAndroidTheme {
        val groupDetailViewModel: GroupDetailViewModel = hiltViewModel()
        ConfirmedScreen(
            groupId = 1,
            confirmedEntities = groupDetailViewModel.mockGroupDetail.confirmedEntities,
            onItemClicked = { _, _, _ -> }
        )
    }
}
