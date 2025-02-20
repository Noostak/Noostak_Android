package com.sopt.core.designsystem.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.showIf

@Composable
fun NoostakTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    style: TextStyle = NoostakTheme.typography.b2Regular,
    isMainAppBar: Boolean = false,
    isIconVisible: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    @DrawableRes iconResource: Int = R.drawable.ic_back_24,
    paddingStart: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 7.dp, vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = stringResource(id = R.string.ic_back),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable { onBackButtonClick() }
                .showIf(isIconVisible),
            tint = NoostakTheme.colors.black
        )
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .showIf(isMainAppBar)
                    .padding(end = 6.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
                contentDescription = null
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = style,
                color = NoostakTheme.colors.black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakTopAppBarPreview() {
    NoostakAndroidTheme {
        NoostakTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            isIconVisible = true,
            title = "그룹"
        )
    }
}
