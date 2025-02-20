package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.scrollToItem
import com.sopt.core.extension.showIf
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.presentation.R

@Composable
fun CalendarGroup(
    groups: List<CalendarGroupEntity>,
    showAddDialog: Boolean = false,
    onAddBtnClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    var selectedGroup by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .showIf(!showAddDialog)
                .padding(top = 5.dp, end = 16.dp)
                .size(46.dp)
                .background(
                    color = NoostakTheme.colors.black,
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
                .zIndex(2f)
                .noRippleClickable { onAddBtnClick() }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = null,
                tint = NoostakTheme.colors.white
            )
        }
        Box(
            modifier = Modifier
                .zIndex(1f)
                .padding(end = 39.dp)
                .size(width = 41.dp, height = 80.dp)
                .align(Alignment.CenterEnd)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            NoostakTheme.colors.white
                        )
                    ),
                    shape = RectangleShape
                )
        )
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = groups, key = { _, item -> item.id }) { index, group ->
                if (index == 0) {
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                }
                CalendarGroupItem(
                    data = group,
                    isSelected = index == selectedGroup,
                    onClick = {
                        selectedGroup = index
                        listState.scrollToItem(coroutineScope, density, index)
                    }
                )
                if (index == groups.lastIndex) {
                    Spacer(
                        modifier = Modifier.width(41.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarGroupPreview() {
    NoostakAndroidTheme {
        CalendarGroup(
            groups = listOf(
                CalendarGroupEntity(
                    id = 1,
                    groupName = "가응가",
                    groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
                ),
                CalendarGroupEntity(
                    id = 2,
                    groupName = "먼지 난다",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 3,
                    groupName = "유잔면",
                    groupImage = "https://avatars.githubusercontent.com/u/68536115?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 4,
                    groupName = "마늘",
                    groupImage = "https://avatars.githubusercontent.com/u/79982452?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 5,
                    groupName = "누스탁1",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 6,
                    groupName = "누스탁2",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 7,
                    groupName = "누스탁3",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 8,
                    groupName = "누스탁4",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                ),
                CalendarGroupEntity(
                    id = 9,
                    groupName = "누스탁5",
                    groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
                )
            )
        )
    }
}
