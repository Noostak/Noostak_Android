package com.sopt.core.designsystem.component.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.Gray200
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.CellType
import com.sopt.core.util.timetable.TimeTable
import com.sopt.domain.entity.AvailableTimeEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity

@Composable
fun NoostakTimeTable(
    data: TimeTableEntity,
    modifier: Modifier = Modifier
) {
    val days = data.timeEntity.size
    val timeSlots = TimeTable().calculateTimeSlots(data.startTime, data.endTime)

    LazyColumn(
        modifier = modifier
            .border(
                width = 1.dp,
                color = NoostakTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        // 행 반복
        items(timeSlots + 1) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                // 열 반복
                for (columnIndex in 0..days) {
                    val cellType = TimeTable().determineCellType(rowIndex, columnIndex)
                    val backgroundColor = TimeTable().getBackgroundColor(cellType, rowIndex, columnIndex, data)
                    val text = TimeTable().getCellText(cellType, rowIndex, columnIndex, data)
                    val shape = when (rowIndex to columnIndex) {
                        0 to 0 -> RoundedCornerShape(topStart = 8.dp)
                        0 to days -> RoundedCornerShape(topEnd = 8.dp)
                        timeSlots to days -> RoundedCornerShape(bottomEnd = 8.dp)
                        timeSlots to 0 -> RoundedCornerShape(bottomStart = 8.dp)
                        else -> RoundedCornerShape(0.dp)
                    }

                    Box(
                        modifier = when (cellType) {
                            CellType.Blank -> Modifier
                                .width(42.dp) // 고정 너비
                                .height(36.dp) // 고정 높이
                            CellType.TimeHeader -> Modifier
                                .width(42.dp)
                                .height(32.dp)

                            CellType.DateHeader -> Modifier
                                .weight(1f) // 날짜 셀은 남은 공간 비율로 채움
                                .height(36.dp)

                            else -> Modifier
                                .weight(1f) // 나머지 셀은 남은 공간 비율로 채움
                                .height(32.dp)
                        }
                            .background(
                                color = backgroundColor,
                                shape = shape
                            )
                            .drawBehind {
                                val borderWidth = 1.dp.toPx()
                                val borderColor = Gray200

                                // 위쪽 선 그리기
                                if (rowIndex > 0) {
                                    drawLine(
                                        color = borderColor,
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = borderWidth
                                    )
                                }

                                // 왼쪽 선 그리기
                                if (columnIndex > 0) {
                                    drawLine(
                                        color = borderColor,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = borderWidth
                                    )
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            style = NoostakTheme.typography.c4Regular,
                            color = NoostakTheme.colors.gray600,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakTimeTable1Preview() {
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
                        ),
                        AvailableTimeEntity(
                            startTime = "17:00",
                            endTime = "18:00",
                            level = 80
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            NoostakTimeTable(data = data)
        }
    }
}