package com.sopt.core.designsystem.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

@Composable
fun NoostakEmptyScreen(@StringRes emptyText: Int, color: Color, style: TextStyle) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(emptyText),
            color = color,
            style = style,
        )
    }
}
