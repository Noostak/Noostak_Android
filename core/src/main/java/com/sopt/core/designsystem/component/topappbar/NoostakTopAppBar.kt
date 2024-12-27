package com.sopt.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.NoostakTypography
import com.sopt.core.extension.showIf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoostakTopAppBar(
    title: String = "",
    modifier: Modifier = Modifier,
    isIconVisible: Boolean,
    onBackButtonClick: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = NoostakTheme.typography.b2Regular,
                color = NoostakTheme.colors.black
            )
        },
        navigationIcon = {
            IconButton(
                modifier = modifier.showIf(isIconVisible),
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
            isIconVisible = false,
            title = "그룹",
        )
    }
}