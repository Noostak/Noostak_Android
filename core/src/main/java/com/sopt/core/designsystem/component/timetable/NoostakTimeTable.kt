package com.sopt.core.designsystem.component.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.core.designsystem.theme.Blue400
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakColors
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun NoostakTimeTable(
    days: Int,
    time: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(
                width = 1.dp,
                color = NoostakTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp)
            ),
        columns = GridCells.Fixed(days + 1)
    ) {
        items((days + 1) * (time + 1)) { index ->
            NoostakTimeTableBox(
                index = index,
                days = days,
                time = time
            )
        }
    }
}

@Composable
fun NoostakTimeTableBox(
    index: Int,
    days: Int,
    time: Int
) {
    val shape = when (index) {
        0 -> RoundedCornerShape(topStart = 10.dp)
        days -> RoundedCornerShape(topEnd = 10.dp)
        (days + 1) * time -> RoundedCornerShape(bottomStart = 10.dp)
        (days + 1) * (time + 1) - 1 -> RoundedCornerShape(bottomEnd = 10.dp)
        else -> RoundedCornerShape(0.dp)
    }
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = NoostakTheme.colors.gray200,
                shape = shape
            )
            .background(
                color = backgroundColor,
                shape = shape
            )
            .defaultMinSize(minWidth = 42.dp, minHeight = 36.dp)
            .then(
                if (getTimeTableText(index, days, time).isEmpty()) {
                    Modifier.clickable(
                        indication = null,
                        interactionSource = NoRippleInteractionSource
                    ) {
                        // 텍스트가 비어 있을 때 클릭 가능하고, 색상 변경
                        backgroundColor =
                            if (backgroundColor == Color.Transparent) Blue400 else Color.Transparent
                        val dateInfo = getTimeTableText(index % (days + 1), days, time)
                        val timeInfo = getTimeTableText(index / (days + 1) * (days + 1), days, time)
                        Timber.d("Area Clicked: $dateInfo $timeInfo")
                    }
                } else {
                    Modifier // 텍스트가 있을 때 클릭 불가
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            text = getTimeTableText(index, days, time),
            color = NoostakTheme.colors.gray600,
            style = NoostakTheme.typography.c4Regular,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

fun getTimeTableText(index: Int, days: Int, time: Int): String {
    return when {
        index == 0 -> "\n" // 맨 왼쪽 위 빈 셀
        index in 1..days -> {
            // 날짜 헤더
            val today = LocalDate.now()
            val targetDate = today.plusDays((index - 1).toLong())
            val dayOfWeek = targetDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
            val month = "%02d".format(targetDate.monthValue)
            val day = "%02d".format(targetDate.dayOfMonth)
            "$dayOfWeek\n$month/$day"
        }

        index % (days + 1) == 0 -> {
            "${index / (days + 1) + 6}시"
        }

        else -> "" // 빈 셀
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakTimeTablePreview() {
    NoostakAndroidTheme {
        NoostakTimeTable(
            days = 6,
            time = 18
        )
    }
}
