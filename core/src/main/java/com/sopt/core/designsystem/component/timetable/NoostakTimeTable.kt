package com.sopt.core.designsystem.component.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.AvailabilityLevel
import com.sopt.core.type.CellType
import com.sopt.domain.entity.AvailableTimeEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun NoostakTimeTable(
    data: TimeTableEntity,
    modifier: Modifier = Modifier
) {
    val days = data.timeEntity.size
    val timeSlots = calculateTimeSlots(data.startTime, data.endTime)

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(days + 1),
        contentPadding = PaddingValues(0.dp)
    ) {
        items((days + 1) * (timeSlots + 1)) { index ->
            val (rowIndex, columnIndex) = index / (days + 1) to index % (days + 1)

            val cellType = determineCellType(rowIndex, columnIndex)
            val backgroundColor = getBackgroundColor(cellType, rowIndex, columnIndex, data)
            val text = getCellText(cellType, rowIndex, columnIndex, data)

            NoostakTimeTableBox(
                index = index,
                days = days,
                timeSlots = timeSlots,
                backgroundColor = backgroundColor,
                text = text
            )
        }
    }
}

@Composable
fun getBackgroundColor(
    cellType: CellType,
    rowIndex: Int,
    columnIndex: Int,
    data: TimeTableEntity
): Color = when (cellType) {
    CellType.Blank, CellType.DateHeader, CellType.TimeHeader -> Color.Transparent
    CellType.Data -> {
        val startHour = data.startTime.split(":")[0].toInt()
        val currentHour = startHour + (rowIndex - 1)
        val availableTimes = data.timeEntity.getOrNull(columnIndex - 1)?.times

        val matchingLevel = availableTimes?.find { timeEntity ->
            val entityStartHour = timeEntity.startTime.split(":")[0].toInt()
            val entityEndHour = timeEntity.endTime.split(":")[0].toInt()
            currentHour in entityStartHour until entityEndHour
        }?.level

        getColorByLevel(matchingLevel ?: 0)
    }
}

fun getCellText(
    cellType: CellType,
    rowIndex: Int,
    columnIndex: Int,
    data: TimeTableEntity
): String {
    val startHour = data.startTime.split(":")[0].toInt()

    return when (cellType) {
        CellType.Blank -> "\n"
        CellType.DateHeader -> {
            val dateEntity = data.timeEntity.getOrNull(columnIndex - 1)
            val date = dateEntity?.date ?: ""
            formatDateHeader(date)
        }

        CellType.TimeHeader -> "${startHour + (rowIndex - 1)}시"
        CellType.Data -> ""
    }
}

@Composable
fun NoostakTimeTableBox(
    index: Int,
    days: Int,
    timeSlots: Int,
    backgroundColor: Color,
    text: String
) {
    val shape = when (index) {
        0 -> RoundedCornerShape(topStart = 10.dp)
        days -> RoundedCornerShape(topEnd = 10.dp)
        (days + 1) * timeSlots -> RoundedCornerShape(bottomStart = 10.dp)
        (days + 1) * (timeSlots + 1) - 1 -> RoundedCornerShape(bottomEnd = 10.dp)
        else -> RoundedCornerShape(0.dp)
    }

    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = NoostakTheme.colors.gray100,
                shape = shape
            )
            .background(
                color = backgroundColor,
                shape = shape
            )
            .defaultMinSize(minWidth = 42.dp, minHeight = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            text = text,
            color = NoostakTheme.colors.gray600,
            style = NoostakTheme.typography.c4Regular,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

fun determineCellType(rowIndex: Int, columnIndex: Int): CellType = when {
    rowIndex == 0 && columnIndex == 0 -> CellType.Blank
    rowIndex == 0 -> CellType.DateHeader
    columnIndex == 0 -> CellType.TimeHeader
    else -> CellType.Data
}

@Composable
fun getColorByLevel(level: Int): Color =
    when (AvailabilityLevel.entries.firstOrNull { level in it.range }) {
        AvailabilityLevel.NONE -> Color.Transparent
        AvailabilityLevel.FEW -> NoostakTheme.colors.blue50
        AvailabilityLevel.SOME -> NoostakTheme.colors.blue200
        AvailabilityLevel.MANY -> NoostakTheme.colors.blue400
        AvailabilityLevel.MOST -> NoostakTheme.colors.blue700
        else -> NoostakTheme.colors.blue800
    }

fun calculateTimeSlots(startTime: String, endTime: String): Int {
    val startHour = startTime.split(":")[0].toInt()
    val endHour = endTime.split(":")[0].toInt()
    return endHour - startHour
}

fun formatDateHeader(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parsedDate = LocalDate.parse(date, formatter)

    val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    val month = "%02d".format(parsedDate.monthValue)
    val day = "%02d".format(parsedDate.dayOfMonth)

    return "$dayOfWeek\n$month/$day"
}

@Preview(showBackground = true)
@Composable
fun NoostakTimeTablePreview() {
    NoostakAndroidTheme {
        val data = TimeTableEntity(
            startTime = "09:00",
            endTime = "18:00",
            timeEntity = listOf(
                TimeEntity(
                    date = "2024-09-27",
                    times = listOf(
                        AvailableTimeEntity(
                            startTime = "09:00",
                            endTime = "10:00",
                            level = 20
                        ),
                        AvailableTimeEntity(
                            startTime = "12:00",
                            endTime = "13:00",
                            level = 40
                        ),
                        AvailableTimeEntity(
                            startTime = "15:00",
                            endTime = "16:00",
                            level = 60
                        )
                    )
                ),
                TimeEntity(
                    date = "2024-09-28",
                    times = listOf(
                        AvailableTimeEntity(
                            startTime = "11:00",
                            endTime = "12:00",
                            level = 20
                        ),
                        AvailableTimeEntity(
                            startTime = "14:00",
                            endTime = "15:00",
                            level = 40
                        ),
                        AvailableTimeEntity(
                            startTime = "17:00",
                            endTime = "18:00",
                            level = 80
                        )
                    )
                )
            )
        )

        NoostakTimeTable(data = data)
    }
}
