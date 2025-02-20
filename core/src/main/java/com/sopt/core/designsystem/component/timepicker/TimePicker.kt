package com.sopt.core.designsystem.component.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme.colors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import com.sopt.core.util.timepicker.TimePicker

@Composable
fun TimePicker(
    items: List<String>,
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    onSelectedItemChange: (String) -> Unit
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listStartIndex = TimePicker().calculateStartIndex(items, startIndex, visibleItemsMiddle, listScrollCount)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var selectedItem by remember { mutableStateOf("") }

    LaunchedEffect(listStartIndex) {
        listState.scrollToItem(listStartIndex)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> TimePicker().getItem(index + visibleItemsMiddle, items) }
            .distinctUntilChanged()
            .collect { item ->
                selectedItem = item
                onSelectedItemChange(item)
            }
    }

    Box(
        modifier = modifier.height(152.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(
                modifier = Modifier.padding(start = 11.dp, end = 11.dp),
                thickness = 1.dp,
                color = colors.gray200
            )
            Spacer(modifier = Modifier.height(19.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = colors.blue100,
                        shape = cornerShape
                    )
                    .padding(horizontal = 20.dp, vertical = 7.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(
                modifier = Modifier.padding(start = 11.dp, end = 11.dp),
                thickness = 1.dp,
                color = colors.gray200
            )
        }
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listScrollCount) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TimePicker().getItemHeight(index, listState))
                        .wrapContentHeight(TimePicker().getItemAlignment(index, listState)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = TimePicker().getItem(index, items).padStart(2, '0'),
                        style = TimePicker().getItemStyle(index, listState),
                        modifier = Modifier.padding(start = TimePicker().getItemPadding(index, listState))
                    )
                    Text(
                        text = "00",
                        style = TimePicker().getItemStyle(index, listState),
                        modifier = Modifier.padding(end = TimePicker().getItemPadding(index, listState))
                    )
                }
            }
        }
    }
}
