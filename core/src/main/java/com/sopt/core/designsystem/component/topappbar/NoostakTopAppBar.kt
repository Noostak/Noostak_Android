package com.sopt.core.designsystem.component.topappbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.showIf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoostakTopAppBar(
    title: String = "",
    style: TextStyle = NoostakTheme.typography.b2Regular,
    modifier: Modifier = Modifier,
    isIconVisible: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    @DrawableRes iconResource: Int = R.drawable.ic_back_24,
    paddingStart: Dp = 8.dp
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = style,
                color = NoostakTheme.colors.black
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = stringResource(id = R.string.ic_back),
                modifier = Modifier
                    .padding(start = paddingStart)
                    .noRippleClickable { onBackButtonClick() }
                    .showIf(isIconVisible),
                tint = NoostakTheme.colors.black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(White),
        windowInsets = WindowInsets(0)
    )
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
