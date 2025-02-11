package com.sopt.presentation.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.presentation.group.component.GroupImage

@Composable
fun CalendarGroupItem(
    modifier: Modifier = Modifier,
    data: CalendarGroupEntity,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(56.dp)
            .noRippleClickable { onClick() }
            .then(if (isSelected) Modifier.graphicsLayer {
                alpha = 1f
            } else Modifier.graphicsLayer { alpha = 0.6f }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        GroupImage(
            imageUrl = data.groupImage,
            modifier = Modifier
                .size(56.dp)
                .aspectRatio(1f),
            cornerRadius = 12.dp
        )
        Text(
            text = data.groupName,
            style = if (isSelected) NoostakTheme.typography.c3SemiBold else NoostakTheme.typography.c3Regular,
            color = NoostakTheme.colors.gray900,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarGroupItemPreview() {
    NoostakAndroidTheme {
        CalendarGroupItem(
            data = CalendarGroupEntity(
                id = 0,
                groupName = "누스탁",
                groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
            ),
            isSelected = true
        )
    }
}
