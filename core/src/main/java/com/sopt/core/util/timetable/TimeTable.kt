package com.sopt.core.util.timetable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.AvailabilityLevel
import com.sopt.core.type.CellType
import com.sopt.domain.entity.PeriodEntity
import com.sopt.domain.entity.TimeTableEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class TimeTable {
    // 시간 차이를 계산 (24시간 형식 기준)
    fun calculateTimeSlots(startTime: String, endTime: String): Int {
        val startHour = extractHour(extractTime(startTime))
        val endHour = extractHour(extractTime(endTime))
        return endHour - startHour
    }

    fun determineCellType(rowIndex: Int, columnIndex: Int): CellType = when {
        rowIndex == 0 && columnIndex == 0 -> CellType.Blank
        rowIndex == 0 -> CellType.DateHeader
        columnIndex == 0 -> CellType.TimeHeader
        else -> CellType.Data
    }

    @Composable
    fun getBackgroundColor(
        cellType: CellType,
        rowIndex: Int,
        columnIndex: Int,
        availablePeriods: PeriodEntity,
        availableTimes: TimeTableEntity
    ): Color {
        return when (cellType) {
            CellType.Blank, CellType.DateHeader, CellType.TimeHeader -> Color.Transparent
            CellType.Data -> {
                val startHour = extractHour(extractTime(availablePeriods.startTime))
                val currentHour = startHour + (rowIndex - 1)

                val date =
                    availablePeriods.dates.getOrNull(columnIndex - 1) ?: return Color.Transparent
                // 해당 열(columnIndex) 날짜 데이터 가져오기
                val formattedDate = extractDate(date)
                val availableTimesForDate = availableTimes.members.flatMap { member ->
                    member.times.filter { extractDate(it.date) == formattedDate }
                }

                // 현재 시간에 해당하는 가능 레벨 계산
                val totalMembers = availableTimes.members.size
                val availableMembers = availableTimesForDate.count { availableTime ->
                    availableTime.times.any { timeEntity ->
                        val entityStartHour = extractHour(extractTime(timeEntity.memberStartTime))
                        val entityEndHour = extractHour(extractTime(timeEntity.memberEndTime))
                        currentHour in entityStartHour until entityEndHour
                    }
                }
                val percentage =
                    if (totalMembers == 0) 0 else (availableMembers * 100 / totalMembers)
                getColorByLevel(percentage)
            }
        }
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

    fun getCellText(
        cellType: CellType,
        rowIndex: Int,
        columnIndex: Int,
        data: PeriodEntity
    ): String {
        // 셀 텍스트 결정 로직
        val startHour = extractHour(extractTime(data.startTime))

        return when (cellType) {
            CellType.Blank -> "\n"
            CellType.DateHeader -> {
                if (columnIndex == 0) ""
                else formatDateTimeToCustomFormat(data.dates[columnIndex - 1])
            }

            CellType.TimeHeader -> "${startHour + (rowIndex - 1)}시"
            CellType.Data -> ""
        }
    }

    fun formatDateTimeToCustomFormat(dateTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val parsedDate = LocalDateTime.parse(dateTime, formatter)
        val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        val month = "%02d".format(parsedDate.monthValue)
        val day = "%02d".format(parsedDate.dayOfMonth)

        return "$dayOfWeek\n$month/$day"
    }

    // 공통적으로 사용하는 시간 추출 로직
    private fun extractTime(dateTime: String): String = dateTime.substringAfter('T')

    // 공통적으로 사용하는 날짜 추출 로직
    private fun extractDate(dateTime: String): String = dateTime.substringBefore('T')

    // 공통적으로 사용하는 시간만 숫자로 변환
    private fun extractHour(time: String): Int = time.substringBefore(':').toInt()
}