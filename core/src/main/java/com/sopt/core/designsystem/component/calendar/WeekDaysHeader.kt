package com.sopt.core.designsystem.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.WeekDayType

@Composable
fun WeekDaysHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NoostakTheme.colors.white),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WeekDayType.entries.forEach { day ->
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 14.dp,
                        vertical = 9.dp
                    ),
                text = day.koreanDay,
                style = NoostakTheme.typography.c3Regular,
                color = NoostakTheme.colors.gray800,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeekDaysHeaderPreview() {
    NoostakAndroidTheme {
        WeekDaysHeader()
    }
}
