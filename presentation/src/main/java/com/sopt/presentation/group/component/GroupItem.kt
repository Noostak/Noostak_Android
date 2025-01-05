package com.sopt.presentation.group.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.GroupEntity

@Composable
fun GroupItem(
    data: GroupEntity,
    onItemClick: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onItemClick(data.groupId) }
            .padding(vertical = 14.dp, horizontal = 6.dp)
    ) {
        GroupImage(
            imageUrl = "https://item.elandrs.com/r/image/item/2023-03-29/21e48456-d3bf-4dee-9f3f-8e516f344175.jpg?w=750&h=&q=100",
            modifier = Modifier
                .size(44.dp)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = data.groupName,
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b4SemiBold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = data.groupPersonnel.toString(),
            color = NoostakTheme.colors.gray700,
            style = NoostakTheme.typography.b4Regular,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupItemPreview() {
    NoostakAndroidTheme {
        GroupItem(
            data = GroupEntity(1, "누스탁", 1, null),
            onItemClick = {}
        )
    }
}
