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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.NoostakTheme.colors
import com.sopt.core.extension.noRippleClickable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun TimePicker(
    items: List<String>,
    state: PickerState = rememberPickerState(),
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp)
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listStartIndex) {
        listState.scrollToItem(listStartIndex)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
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
                modifier = Modifier.padding(start = 10.5.dp, end = 10.5.dp), // 디자인 변경시 변경(요청드렷움)
                thickness = 1.dp,
                color = colors.gray200
            )
            Spacer(modifier = Modifier.height(19.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = NoostakTheme.colors.blue100,
                        shape = cornerShape
                    )
                    .padding(horizontal = 20.dp, vertical = 7.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(
                modifier = Modifier.padding(start = 10.5.dp, end = 10.5.dp),
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
                PickerItem(
                    text = getItem(index).padStart(2, '0'),
                    isFirstItem = index == listState.firstVisibleItemIndex,
                    isSecondItem = index == listState.firstVisibleItemIndex + 1
                )
            }
        }
    }
}

@Composable
fun PickerItem(text: String, isFirstItem: Boolean, isSecondItem: Boolean) {
    val style = when {
        isSecondItem -> NoostakTheme.typography.h3SemiBold
        else -> NoostakTheme.typography.h4SemiBold.copy(color = colors.gray500)
    }
    val modifier = when {
        isSecondItem -> 20.dp
        else -> 30.dp
    }
    val height = when {
        isSecondItem -> 48.dp
        isFirstItem -> 52.dp
        else -> 52.dp
    }
    val alignment = when {
        isSecondItem -> Alignment.CenterVertically
        isFirstItem -> Alignment.Top
        else -> Alignment.Bottom
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .wrapContentHeight(alignment),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = style,
            modifier = Modifier.padding(start = modifier)
        )
        Text(
            text = "00",
            style = style,
            modifier = Modifier.padding(end = modifier)
        )
    }
}

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}

@Composable
fun NoostakTimePicker(
    onTimeSelected: (startHour: Int, endHour: Int) -> Unit
) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    var selectedStartHour by remember { mutableIntStateOf(0) }
    val selectedStartMinute by remember { mutableIntStateOf(0) }
    var selectedEndHour by remember { mutableIntStateOf(23) }
    val selectedEndMinute by remember { mutableIntStateOf(0) }

    var isStartTimeEditing by remember { mutableStateOf(true) }

    val values = remember { (0..23).map { it.toString() } }

    val valuesPickerState = rememberPickerState()

    LaunchedEffect(isStartTimeEditing) {
        valuesPickerState.selectedItem = if (isStartTimeEditing) {
            selectedStartHour.toString().padStart(2, '0')
        } else {
            selectedEndHour.toString().padStart(2, '0')
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(301.dp)
            .background(colors.gray50, RoundedCornerShape(20.dp))
            .padding(top = 27.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 58.5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeDisplay(
                label = stringResource(R.string.text_noostak_time_picker_start),
                hour = selectedStartHour,
                minute = selectedStartMinute,
                isSelected = isStartTimeEditing,
                onClick = {
                    isStartTimeEditing = true
                }
            )
            Text(
                text = stringResource(R.string.text_noostak_time_picker_wave),
                style = typography.h1Bold,
                color = colors.gray700
            )
            TimeDisplay(
                label = stringResource(R.string.text_noostak_time_picker_end),
                hour = selectedEndHour,
                minute = selectedEndMinute,
                isSelected = !isStartTimeEditing,
                onClick = {
                    isStartTimeEditing = false
                }
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp, bottom = 30.dp, start = 32.dp, end = 32.dp)
                .clip(CircleShape),
            thickness = 3.dp,
            color = colors.gray100
        )

        Row(
            modifier = Modifier
                .padding(bottom = 27.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimePicker(
                state = valuesPickerState,
                items = values,
                visibleItemsCount = 3,
                startIndex = if (isStartTimeEditing) selectedStartHour else selectedEndHour,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 75.dp, end = 75.dp)
                    .fillMaxWidth(),
                cornerShape = RoundedCornerShape(30.dp)
            )
        }
        LaunchedEffect(valuesPickerState.selectedItem) {
            if (isStartTimeEditing) {
                selectedStartHour = valuesPickerState.selectedItem.toIntOrNull() ?: 0
            } else {
                selectedEndHour = valuesPickerState.selectedItem.toIntOrNull() ?: 0
            }
            onTimeSelected(selectedStartHour, selectedEndHour)
        }
    }
}

@Composable
fun TimeDisplay(
    label: String,
    hour: Int,
    minute: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    val textColor = if (isSelected) colors.blue600 else colors.gray900

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.noRippleClickable {
            onClick()
        }
    ) {
        Text(
            text = label,
            style = typography.c3Regular,
            color = colors.gray700
        )
        Text(
            text = String.format("%02d:%02d", hour, minute),
            style = typography.h1Bold.copy(color = textColor)
        )
    }
}
