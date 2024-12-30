package com.sopt.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakChip(
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    backgroundColor: Color,
    borderColor: Color,
    horizontalPaddingValues: Dp,
    verticalPaddingValues: Dp
) {
    Text(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = horizontalPaddingValues, vertical = verticalPaddingValues),
        text = text,
        style = textStyle,
        color = textColor,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun NoostakChipPreview() {
    NoostakAndroidTheme {
        NoostakChip(
            text = "이가을",
            textStyle = NoostakTheme.typography.c3Regular,
            textColor = NoostakTheme.colors.gray900,
            backgroundColor = NoostakTheme.colors.blue200,
            borderColor = NoostakTheme.colors.blue200,
            horizontalPaddingValues = 8.dp,
            verticalPaddingValues = 6.dp
        )
    }
}
