package com.sopt.core.designsystem.component.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
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
        modifier = modifier
            .border(
                width = 1.dp,
                color = NoostakTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp)
            ),
        columns = GridCells.Fixed(days + 1)
    ) {
        items((days + 1) * (timeSlots + 1)) { index ->
            val rowIndex = index / (days + 1)
            val columnIndex = index % (days + 1)

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
): Color {
    return when (cellType) {
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
                color = NoostakTheme.colors.gray200,
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

fun determineCellType(rowIndex: Int, columnIndex: Int): CellType {
    return when {
        rowIndex == 0 && columnIndex == 0 -> CellType.Blank
        rowIndex == 0 -> CellType.DateHeader
        columnIndex == 0 -> CellType.TimeHeader
        else -> CellType.Data
    }
}

enum class CellType {
    Blank, DateHeader, TimeHeader, Data
}

@Composable
fun getColorByLevel(level: Int): Color {
    return when (level) {
        0 -> Color.Transparent
        in 1..20 -> NoostakTheme.colors.blue50
        in 21..40 -> NoostakTheme.colors.blue200
        in 41..60 -> NoostakTheme.colors.blue400
        in 61..80 -> NoostakTheme.colors.blue700
        else -> NoostakTheme.colors.blue800
    }
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
