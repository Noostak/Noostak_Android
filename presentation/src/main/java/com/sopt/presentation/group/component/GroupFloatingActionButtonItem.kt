package com.sopt.presentation.group.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R

@Composable
fun GroupFloatingActionButtonItem(
    painter: Painter,
    text: String,
    onItemClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(134.dp)
            .noRippleClickable { onItemClick() }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 6.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
                .size(24.dp)
                .aspectRatio(1f)
        )
        Text(
            text = text,
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.b4Regular,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupFloatingActionButtonItemPreview() {
    NoostakAndroidTheme {
        GroupFloatingActionButtonItem(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            text = "그룹 만들기",
            onItemClick = {}
        )
    }
}
