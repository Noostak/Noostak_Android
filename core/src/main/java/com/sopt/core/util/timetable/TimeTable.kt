package com.sopt.core.util.timetable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.AvailabilityLevel
import com.sopt.core.type.CellType
import com.sopt.domain.entity.TimeTableEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class TimeTable {
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
}