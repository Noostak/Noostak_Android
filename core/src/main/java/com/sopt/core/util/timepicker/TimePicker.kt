package com.sopt.core.util.timepicker

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.NoostakTheme.colors

class TimePicker {
    fun timeFormat(hour: Int, minute: Int): String {
        val time = String.format("%02d:%02d", hour, minute)
        return time
    }

    fun calculateStartIndex(items: List<String>, startIndex: Int, visibleItemsMiddle: Int, listScrollCount: Int): Int {
        val listScrollMiddle = listScrollCount / 2
        return listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex
    }

    fun getItem(index: Int, items: List<String>): String = items[index % items.size]

    @Composable
    fun getItemStyle(index: Int, listState: LazyListState): TextStyle {
        val isSecondItem = index == listState.firstVisibleItemIndex + 1
        return when {
            isSecondItem -> NoostakTheme.typography.h3SemiBold
            else -> NoostakTheme.typography.h4SemiBold.copy(color = colors.gray500)
        }
    }

    fun getItemPadding(index: Int, listState: LazyListState): Dp {
        val isSecondItem = index == listState.firstVisibleItemIndex + 1
        return when {
            isSecondItem -> 20.dp
            else -> 30.dp
        }
    }

    fun getItemHeight(index: Int, listState: LazyListState): Dp {
        val isFirstItem = index == listState.firstVisibleItemIndex
        val isSecondItem = index == listState.firstVisibleItemIndex + 1
        return when {
            isSecondItem -> 48.dp
            isFirstItem -> 52.dp
            else -> 52.dp
        }
    }

    fun getItemAlignment(index: Int, listState: LazyListState): Alignment.Vertical {
        val isFirstItem = index == listState.firstVisibleItemIndex
        val isSecondItem = index == listState.firstVisibleItemIndex + 1
        return when {
            isSecondItem -> Alignment.CenterVertically
            isFirstItem -> Alignment.Top
            else -> Alignment.Bottom
        }
    }
}
