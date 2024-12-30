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
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.ConfirmedEntity
import com.sopt.presentation.R

@Composable
fun ConfirmedScreen(
    groupId: Long,
    completes: List<ConfirmedEntity>,
    onItemClicked: (Long, Long) -> Unit
) {
    if (completes.isEmpty()) {
        Text(
            modifier = Modifier.padding(top = 103.dp),
            text = stringResource(R.string.tv_group_detail_no_complete),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b2Regular
        )
    } else {
        LazyColumn {
            items(completes, key = { it.id }) {
                ConfirmedItem(
                    groupId = groupId,
                    confirmed = it,
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
    confirmed: ConfirmedEntity,
    onItemClicked: (Long, Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onItemClicked(groupId, confirmed.id) }
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
                    text = confirmed.title,
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.b1SemiBold,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp),
                    text = confirmed.date,
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
        ConfirmedScreen(
            groupId = 1,
            completes = listOf(
                ConfirmedEntity(
                    id = 1,
                    title = "약속 제목",
                    date = "2024년 9월 7일 일요일"
                )
            ),
            onItemClicked = { _, _ -> }
        )
    }
}