package com.sopt.presentation.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.image.ProfileImage
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.GroupEntity
import com.sopt.presentation.R

@Composable
fun CalendarGroupItem(
    modifier: Modifier = Modifier,
    data: GroupEntity,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(56.dp)
            .noRippleClickable { onClick() }
            .then(
                Modifier.graphicsLayer {
                    alpha = if (isSelected) 1f else 0.6f
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ProfileImage(
            imageUrl = data.groupProfileImageUrl,
            modifier = Modifier
                .size(56.dp)
                .aspectRatio(1f),
            shape = RoundedCornerShape(12.dp),
            placeholder = R.drawable.ic_group_profile
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
            data = GroupEntity(
                groupId = 0,
                groupName = "그룹 이름",
                groupMemberCount = 1,
                groupProfileImageUrl = ""
            ),
            isSelected = true
        )
    }
}
