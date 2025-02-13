package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun ScheduleItem(
    title: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(start = 1.dp)
            .background(NoostakTheme.colors.white),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 2.dp, height = 12.dp)
                .background(color = color, shape = RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = title,
            style = NoostakTheme.typography.c5Regular,
            color = NoostakTheme.colors.gray800,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(end = 1.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ScheduleItemPreview() {
    NoostakAndroidTheme {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(NoostakTheme.colors.white)
        ) {
            Column {
                ScheduleItem(title = "제목 없음", color = NoostakTheme.colors.blue500)
            }
        }
    }
}
