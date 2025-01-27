package com.sopt.core.designsystem.component.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakProgressBar(progressBar: List<Boolean>) {
    Row {
        progressBar.forEachIndexed { index, isLong ->
            Box(
                modifier = if (isLong) {
                    Modifier
                        .width(61.dp)
                        .height(13.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(color = NoostakTheme.colors.blue300)
                } else {
                    Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(color = NoostakTheme.colors.blue300)
                }
            )
            if (index < progressBar.size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
