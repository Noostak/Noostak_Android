package com.sopt.core.designsystem.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakCloseAppBar(
    modifier: Modifier,
    onBackButtonClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = NoostakTheme.colors.white)
            .height(dimensionResource(id = R.dimen.appbar_height))
    ) {
        IconButton(
            onClick = onBackButtonClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.appbar_height))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_appbar_close),
                contentDescription = "Icon Delete On AppBar"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakCloseAppBarPreview() {
    NoostakAndroidTheme {
        NoostakCloseAppBar(Modifier)
    }
}
