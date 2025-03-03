package com.sopt.presentation.calendar.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.screen.NoostakEmptyScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.ScheduleEntity
import com.sopt.domain.entity.ScheduleListDetailEntity
import com.sopt.presentation.R

@Composable
fun ScheduleListScreen(
    data: ScheduleEntity,
    onItemClick: (ScheduleListDetailEntity) -> Unit = {},
    onConfirmBtnClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(63.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.date,
                    color = NoostakTheme.colors.black,
                    style = NoostakTheme.typography.b1SemiBold
                )
            }
            HorizontalDivider(color = NoostakTheme.colors.gray100)
            if (data.scheduleList.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 30.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                ) {
                    itemsIndexed(
                        items = data.scheduleList,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        ScheduleItem(data = item, onItemClick = onItemClick)
                    }
                }
            } else {
                NoostakEmptyScreen(
                    emptyText = R.string.text_schedule_list_empty_content,
                    color = NoostakTheme.colors.gray700,
                    style = NoostakTheme.typography.b4Regular
                )
            }
        }
        NoostakBottomButton(
            modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.vertical_padding),
                start = dimensionResource(id = R.dimen.vertical_padding),
                end = dimensionResource(id = R.dimen.vertical_padding)
            ),
            text = "확인",
            activateColor = NoostakTheme.colors.blue600,
            isEnabled = true,
            onButtonClick = {
                onConfirmBtnClick()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleListScreenPreview() {
    NoostakAndroidTheme {
        ScheduleListScreen(
            data = ScheduleEntity(
                date = "1월 13일 (월)",
                scheduleList = listOf(
                    ScheduleListDetailEntity(
                        id = 1,
                        name = "누스탁 회의dasfdsafsafdafdafsdfsafdsafdsadsdafsadfsdfsdafadafdsafsdafsaf",
                        category = "중요",
                        startTime = "1월 13일(월)",
                        endTime = "1월 13일(월)",
                        duration = 24
                    ),
                    ScheduleListDetailEntity(
                        id = 2,
                        name = "누스탁 모각작",
                        category = "일정",
                        startTime = "1월 15일(수)",
                        endTime = "1월 15일(수)",
                        duration = 5
                    ),
                    ScheduleListDetailEntity(
                        id = 3,
                        name = "누스탁 회식",
                        category = "취미",
                        startTime = "1월 20일(화)",
                        endTime = "1월 20일(화)",
                        duration = 2
                    )
                )
            )
        )
    }
}
