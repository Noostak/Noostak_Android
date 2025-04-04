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
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.scrollToItem
import com.sopt.core.extension.showIf
import com.sopt.domain.entity.GroupEntity
import com.sopt.presentation.R

@Composable
fun CalendarGroup(
    groups: List<GroupEntity>,
    showAddDialog: Boolean = false,
    onAddBtnClick: () -> Unit = {},
    onGroupClick: (Long) -> Unit = {}
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
        if (groups.isEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 14.dp, start = 24.dp),
                text = stringResource(R.string.text_calendar_group_empty),
                color = NoostakTheme.colors.gray700,
                style = NoostakTheme.typography.b5Regular,
                textAlign = TextAlign.Start
            )
        } else {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 42.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(items = groups, key = { _, item -> item.groupId }) { index, group ->
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
                            onGroupClick(group.groupId)
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
}

@Preview(showBackground = true)
@Composable
fun CalendarGroupPreview() {
    NoostakAndroidTheme {
        CalendarGroup(
            groups = listOf(
                GroupEntity(
                    groupId = 1,
                    groupName = "가응가",
                    groupMemberCount = 5,
                    groupProfileImageUrl = "https://avatars.githubusercontent.com/u/91470334?v=4"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarGroupPreviewEmpty() {
    NoostakAndroidTheme {
        CalendarGroup(
            groups = emptyList()
        )
    }
}
