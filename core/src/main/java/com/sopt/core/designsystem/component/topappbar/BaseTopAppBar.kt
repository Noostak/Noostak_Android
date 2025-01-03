package com.sopt.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    title: String = "",
    style: TextStyle = NoostakTheme.typography.b2Regular,
    modifier: Modifier,
    isBackButton: Boolean = true,
    onBackButtonClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = style,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            if (isBackButton) {
                IconButton(
                    onClick = {
                        onBackButtonClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.ic_back),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(0.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(White),
        windowInsets = WindowInsets(
            left = 0,
            top = 0,
            right = 0,
            bottom = 0
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BaseTopAppBarPreview() {
    NoostakAndroidTheme {
        BaseTopAppBar(title = "test", modifier = Modifier)
    }
}
