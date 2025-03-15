package com.sopt.presentation.calendar.component.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable

@Composable
fun ScheduleDetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackButtonClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Icon(
            painter = painterResource(
                id = com.sopt.presentation.R.drawable.ic_back_48
            ),
            contentDescription = stringResource(id = R.string.ic_back),
            modifier = Modifier
                .noRippleClickable { onBackButtonClick() }
                .padding(top = 8.dp, bottom = 7.dp),
            tint = NoostakTheme.colors.black
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = NoostakTheme.typography.b1SemiBold,
            color = NoostakTheme.colors.black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleDetailTopAppBarPreview() {
    NoostakAndroidTheme {
        ScheduleDetailTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "그룹\n누스탁"
        )
    }
}
