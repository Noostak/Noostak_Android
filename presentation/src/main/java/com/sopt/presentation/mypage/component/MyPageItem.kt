package com.sopt.presentation.mypage.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource

@Composable
fun MyPageItem(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = com.sopt.presentation.R.dimen.horizontal_padding),
                end = 4.dp
            )
    ) {
        Text(
            text = text,
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b5Regular,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp),
            interactionSource = NoRippleInteractionSource
        ) {
            Icon(
                painter = painterResource(id = com.sopt.presentation.R.drawable.ic_mypage_arrow),
                contentDescription = stringResource(com.sopt.presentation.R.string.icon_my_page_item_arrow_description),
                tint = NoostakTheme.colors.gray800
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageItemPreview() {
    NoostakAndroidTheme {
        MyPageItem(text = "약관 및 정책", onClick = {})
    }
}
