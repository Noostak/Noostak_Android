package com.sopt.core.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun LazyListState.scrollToItem(
    coroutineScope: CoroutineScope,
    density: Density,
    index: Int = 0
) {
    coroutineScope.launch {
        if (index == 0) {
            scrollToItem(index)
        } else {
            animateScrollToItem(
                index = index,
                scrollOffset = with(density) { -30.dp.roundToPx() }
            )
        }
    }
}
