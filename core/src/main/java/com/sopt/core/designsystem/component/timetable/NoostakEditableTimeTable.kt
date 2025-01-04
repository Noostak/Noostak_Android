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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.AvailableTimeEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity

@Composable
fun NoostakEditableTimeTable(
    data: TimeTableEntity,
    modifier: Modifier = Modifier,
    onSelectionChange: (List<TimeEntity>) -> Unit
) {
    val days = data.timeEntity.size
    val timeSlots = calculateTimeSlots(data.startTime, data.endTime)
    val selectedCells = remember { mutableStateListOf<Pair<Int, Int>>() } // Row, Column 저장

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
            val isSelected = selectedCells.contains(rowIndex to columnIndex)
            val backgroundColor =
                getEditableBackgroundColor(cellType, rowIndex, columnIndex, isSelected)
            val text = getCellText(cellType, rowIndex, columnIndex, data)

            NoostakEditableTimeTableBox(
                index = index,
                days = days,
                timeSlots = timeSlots,
                backgroundColor = backgroundColor,
                text = text,
                onClick = {
                    if (cellType == CellType.Data) {
                        val cell = rowIndex to columnIndex
                        if (selectedCells.contains(cell)) {
                            selectedCells.remove(cell)
                        } else {
                            selectedCells.add(cell)
                        }
                        onSelectionChange(
                            selectedCells.toTimeEntities(
                                data.startTime,
                                data.timeEntity
                            )
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun getEditableBackgroundColor(
    cellType: CellType,
    rowIndex: Int,
    columnIndex: Int,
    isSelected: Boolean
): Color {
    return when (cellType) {
        CellType.Blank, CellType.DateHeader, CellType.TimeHeader -> Color.Transparent
        CellType.Data -> if (isSelected) NoostakTheme.colors.blue400 else Color.Transparent
    }
}

@Composable
fun NoostakEditableTimeTableBox(
    index: Int,
    days: Int,
    timeSlots: Int,
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
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
            .defaultMinSize(minWidth = 42.dp, minHeight = 36.dp)
            .noRippleClickable { onClick() },
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

fun List<Pair<Int, Int>>.toTimeEntities(
    startTime: String,
    timeEntityList: List<TimeEntity>
): List<TimeEntity> {
    val startHour = startTime.split(":")[0].toInt()
    val groupedByColumn = this.groupBy { it.second }

    return groupedByColumn.mapNotNull { (columnIndex, cells) ->
        val date = timeEntityList.getOrNull(columnIndex - 1)?.date ?: return@mapNotNull null
        val times = cells.map { cell ->
            val hour = startHour + (cell.first - 1)
            AvailableTimeEntity(
                startTime = "${"%02d".format(hour)}:00",
                endTime = "${"%02d".format(hour + 1)}:00"
            )
        }
        TimeEntity(date = date, times = times)
    }
}
