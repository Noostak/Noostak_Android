package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme.colors
import com.sopt.core.designsystem.theme.NoostakTheme.typography
import com.sopt.core.extension.noRippleClickable
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun NoostakCalendar(
    start: String,
    end: String,
    isSingleDate: Boolean,
    isRangeSelected: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    days: List<String>,
    onShowMessageChange: (Boolean) -> Unit
) {
    var year by remember { mutableIntStateOf(LocalDate.now().year) }
    var month by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    var selectedDates by remember { mutableStateOf<List<String>>(emptyList()) }
    var startDate by remember { mutableStateOf(start) }
    var endDate by remember { mutableStateOf(end) }
    val yearMonth = YearMonth.of(year, month)
    val totalDays = yearMonth.lengthOfMonth()
    val firstDay = LocalDate.of(year, month, 1).dayOfWeek.value % 7

    val onShowSnackBar: () -> Unit = {
        onShowMessageChange(true)
    }

    LaunchedEffect(isSingleDate) {
        selectedDates = emptyList()
        startDate = ""
        endDate = ""
    }

    LaunchedEffect(selectedDates, startDate, endDate) {
        if (isSingleDate) {
            isRangeSelected(selectedDates)
        } else if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            val startLocalDate = LocalDate.parse(startDate)
            val endLocalDate = LocalDate.parse(endDate)
            val rangeDates = (0..endLocalDate.toEpochDay() - startLocalDate.toEpochDay())
                .map { startLocalDate.plusDays(it).toString() }
            isRangeSelected(rangeDates)
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar_left),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .noRippleClickable {
                        if (month == 1) {
                            year -= 1
                            month = 12
                        } else {
                            month -= 1
                        }
                    }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${year}년 ${month}월",
                style = typography.b1SemiBold,
                color = colors.gray900,
                modifier = Modifier.padding(vertical = 13.dp, horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar_right),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .noRippleClickable {
                        if (month == 12) {
                            year += 1
                            month = 1
                        } else {
                            month += 1
                        }
                    }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { day ->
                Text(
                    text = day,
                    style = typography.c3SemiBold,
                    textAlign = TextAlign.Center,
                    color = colors.gray600,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 11.dp)
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            var day = 1
            for (week in 0..5) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (0..6).forEach { dayOfWeek ->
                        val dateText = when {
                            week == 0 && dayOfWeek < firstDay -> ""
                            day > totalDays -> ""
                            else -> (day++).toString()
                        }
                        val dateValue = "$year-${month.toString().padStart(2, '0')}-${
                        dateText.padStart(
                            2,
                            '0'
                        )
                        }"
                        val isSelected = dateValue in selectedDates
                        val isRange = !isSingleDate &&
                            dateText.isNotEmpty() &&
                            startDate.isNotEmpty() &&
                            endDate.isNotEmpty() &&
                            LocalDate.parse(dateValue) in LocalDate.parse(startDate)..LocalDate.parse(
                            endDate
                        )
                        val isStart = dateValue == startDate
                        val isEnd = dateValue == endDate

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isSelected -> {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = colors.blue300,
                                                shape = CircleShape
                                            )
                                    )
                                }

                                isEnd && startDate.isEmpty() -> {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = colors.blue300,
                                                shape = CircleShape
                                            )
                                    )
                                }

                                isStart && endDate.isEmpty() -> {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = colors.blue300,
                                                shape = CircleShape
                                            )
                                    )
                                }

                                isRange && !isStart && !isEnd && startDate.isNotEmpty() && endDate.isNotEmpty() -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .background(colors.blue100)
                                    )
                                }

                                isStart && endDate.isNotEmpty() -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .padding(start = 20.dp)
                                            .background(colors.blue100)
                                    )
                                }

                                isEnd && startDate.isNotEmpty() -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .padding(end = 20.dp)
                                            .background(colors.blue100)
                                    )
                                }
                            }
                            if (isStart || isEnd) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = colors.blue300,
                                            shape = CircleShape
                                        )
                                )
                            }

                            Text(
                                text = dateText,
                                style = typography.c3Regular,
                                textAlign = TextAlign.Center,
                                color = colors.gray900,
                                modifier = Modifier
                                    .padding(vertical = 11.dp)
                                    .noRippleClickable {
                                        if (isSingleDate) {
                                            if (dateValue in selectedDates) {
                                                selectedDates = selectedDates - dateValue
                                            } else {
                                                if (selectedDates.size < 7) {
                                                    selectedDates = selectedDates + dateValue
                                                } else {
                                                    onShowSnackBar()
                                                }
                                            }
                                        } else {
                                            if (dateValue == startDate || dateValue == endDate) {
                                                startDate = ""
                                                endDate = ""
                                                selectedDates = emptyList()
                                                isRangeSelected(emptyList())
                                            } else {
                                                val selectedDate = LocalDate.parse(dateValue)
                                                if (startDate.isEmpty() || (startDate.isNotEmpty() && endDate.isNotEmpty())) {
                                                    startDate = dateValue
                                                    endDate = ""
                                                    isRangeSelected(emptyList())
                                                } else {
                                                    if (LocalDate.parse(startDate).isAfter(selectedDate)) {
                                                        if (LocalDate.parse(startDate).minusDays(6) > selectedDate) {
                                                            endDate = ""
                                                            onShowSnackBar()
                                                        } else {
                                                            endDate = startDate
                                                            startDate = selectedDate.toString()
                                                        }
                                                    } else {
                                                        if (LocalDate.parse(startDate).plusDays(6) < selectedDate) {
                                                            endDate = ""
                                                            onShowSnackBar()
                                                        } else {
                                                            endDate = selectedDate.toString()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakCalendarPreview() {
    NoostakAndroidTheme {
        NoostakCalendar(
            start = "",
            end = "",
            isSingleDate = true,
            isRangeSelected = {},
            days = listOf("일", "월", "화", "수", "목", "금", "토"),
            onShowMessageChange = {}
        )
    }
}
