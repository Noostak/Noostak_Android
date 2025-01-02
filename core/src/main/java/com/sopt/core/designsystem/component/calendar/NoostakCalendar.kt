package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun NoostakCalendar(
    start: String,
    end: String,
    isSingleDate: Boolean,
    isRangeSelected: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    var year by remember { mutableStateOf(LocalDate.now().year) }
    var month by remember { mutableStateOf(LocalDate.now().monthValue) }
    var selectedDates by remember { mutableStateOf<List<String>>(emptyList()) }
    var startDate by remember { mutableStateOf(start) }
    var endDate by remember { mutableStateOf(end) }
    var showMessage by remember { mutableStateOf(false) }

    val yearMonth = YearMonth.of(year, month)
    val totalDays = yearMonth.lengthOfMonth()
    val firstDay = LocalDate.of(year, month, 1).dayOfWeek.value % 7

    LaunchedEffect(isSingleDate) {
        selectedDates = emptyList()
        startDate = ""
        endDate = ""
    }

    LaunchedEffect(showMessage) {
        if (showMessage) {
            kotlinx.coroutines.delay(3000)
            showMessage = false
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar_back),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
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
                text = "$year 년 $month 월",
                style = typography.b1SemiBold,
                color = colors.gray900,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_calendar_front),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        if (month == 12) {
                            year += 1
                            month = 1
                        } else {
                            month += 1
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    text = day,
                    style = typography.c2SemiBold,
                    textAlign = TextAlign.Center,
                    color = colors.gray600,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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

                        val dateValue = "$year-${month.toString().padStart(2, '0')}-${dateText.padStart(2, '0')}"

                        val isSelected = dateValue in selectedDates
                        val isRange = !isSingleDate &&
                                dateText.isNotEmpty() &&
                                startDate.isNotEmpty() &&
                                endDate.isNotEmpty() &&
                                LocalDate.parse(dateValue) in LocalDate.parse(startDate)..LocalDate.parse(endDate)

                        val isStart = dateValue == startDate
                        val isEnd = dateValue == endDate

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isSelected -> {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = colors.blue300,
                                                shape = androidx.compose.foundation.shape.CircleShape
                                            )
                                    )
                                }
                                isRange && !isStart && !isEnd -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .background(colors.blue100)
                                    )
                                }
                                isStart -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .padding(start = 20.dp)
                                            .background(colors.blue100)
                                    )
                                }
                                isEnd -> {
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
                                            shape = androidx.compose.foundation.shape.CircleShape
                                        )
                                )
                            }

                            Text(
                                text = dateText,
                                style = typography.c3Regular,
                                textAlign = TextAlign.Center,
                                color = colors.gray900,
                                modifier = Modifier.clickable(enabled = dateText.isNotEmpty()) {
                                    if (isSingleDate) {
                                        if (dateValue in selectedDates) {
                                            selectedDates = selectedDates - dateValue
                                        } else {
                                            if (selectedDates.size < 7) {
                                                selectedDates = selectedDates + dateValue
                                            } else {
                                                showMessage = true
                                            }
                                        }
                                    } else {
                                        val selectedDate = LocalDate.parse(dateValue)
                                        if (startDate.isEmpty() || (startDate.isNotEmpty() && endDate.isNotEmpty())) {
                                            startDate = dateValue
                                            endDate = ""
                                        } else {
                                            val tempStart = LocalDate.parse(startDate)
                                            val tempEnd = selectedDate
                                            if (tempStart.isAfter(tempEnd)) {
                                                if (tempStart.minusDays(7) > tempEnd) {
                                                    startDate = tempStart.minusDays(7).toString()
                                                    endDate = tempStart.toString()
                                                    showMessage = true
                                                } else {
                                                    endDate = tempStart.toString()
                                                    startDate = tempEnd.toString()
                                                }
                                            } else {
                                                if (tempStart.plusDays(7) < tempEnd) {
                                                    endDate = tempStart.plusDays(7).toString()
                                                    startDate = tempStart.toString()
                                                    showMessage = true
                                                } else {
                                                    startDate = tempStart.toString()
                                                    endDate = tempEnd.toString()
                                                }
                                            }
                                        }
                                        isRangeSelected(startDate, endDate)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showMessage) {
            Spacer(modifier = Modifier.height(1.dp))
            Box(
                modifier = Modifier
                    .width(195.dp)
                    .height(42.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = colors.pink,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp)
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "최대 7일까지 선택할 수 있어요",
                    style = typography.c2SemiBold,
                    color = colors.red01,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}