package com.sopt.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R

@Composable
fun NoostakCalendarChip(
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    backgroundColor: Color,
    borderColor: Color,
    horizontalPaddingValues: Dp,
    verticalPaddingValues: Dp
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.calendar_chip_corner_padding))
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.calendar_chip_corner_padding))
            )
            .defaultMinSize(minWidth = 39.dp)
            .padding(horizontal = horizontalPaddingValues, vertical = verticalPaddingValues)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(),
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
