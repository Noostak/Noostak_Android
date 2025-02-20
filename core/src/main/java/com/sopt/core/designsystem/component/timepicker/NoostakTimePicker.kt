package com.sopt.core.designsystem.component.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.NoostakTheme.colors

@Composable
fun NoostakTimePicker(
    onTimeSelected: (startHour: Int, endHour: Int) -> Unit
) {
    var selectedStartHour by remember { mutableIntStateOf(0) }
    var selectedEndHour by remember { mutableIntStateOf(23) }
    var isStartTimeEditing by remember { mutableStateOf(true) }
    val values = remember { (0..23).map { it.toString() } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(301.dp)
            .background(colors.gray50, RoundedCornerShape(20.dp))
            .padding(top = 27.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 58.5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeDisplay(
                label = stringResource(R.string.text_noostak_time_picker_start),
                hour = selectedStartHour,
                isSelected = isStartTimeEditing,
                onClick = {
                    isStartTimeEditing = true
                }
            )
            Text(
                text = stringResource(R.string.text_noostak_time_picker_wave),
                style = NoostakTheme.typography.h1Bold,
                color = colors.gray700
            )
            TimeDisplay(
                label = stringResource(R.string.text_noostak_time_picker_end),
                hour = selectedEndHour,
                isSelected = !isStartTimeEditing,
                onClick = {
                    isStartTimeEditing = false
                }
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp, bottom = 30.dp, start = 32.dp, end = 32.dp)
                .clip(CircleShape),
            thickness = 3.dp,
            color = colors.gray100
        )
        Row(
            modifier = Modifier
                .padding(bottom = 27.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimePicker(
                items = values,
                visibleItemsCount = 3,
                startIndex = if (isStartTimeEditing) selectedStartHour else selectedEndHour,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 75.dp, end = 75.dp)
                    .fillMaxWidth(),
                cornerShape = RoundedCornerShape(30.dp),
                onSelectedItemChange = { selectedValue ->
                    if (isStartTimeEditing) {
                        selectedStartHour = selectedValue.toIntOrNull() ?: 0
                    } else {
                        selectedEndHour = selectedValue.toIntOrNull() ?: 0
                    }
                    onTimeSelected(selectedStartHour, selectedEndHour)
                }
            )
        }
    }
}
