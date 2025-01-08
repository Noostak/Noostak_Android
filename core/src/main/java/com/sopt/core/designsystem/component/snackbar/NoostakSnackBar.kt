package com.sopt.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

const val SNACK_BAR_DURATION = 2000L

@Composable
fun NoostakSnackBar(
    message: String = "",
    textStyle: TextStyle = NoostakTheme.typography.c3Regular,
    textColor: Color = NoostakTheme.colors.white,
    backgroundColor: Color = NoostakTheme.colors.gray800
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.snack_bar_radius)))
            .background(color = backgroundColor)
            .padding(
                vertical = dimensionResource(id = R.dimen.snack_bar_vertical_padding),
                horizontal = dimensionResource(
                    id = R.dimen.snack_bar_horizontal_padding
                )
            )
    ) {
        Text(text = message, style = textStyle, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakSnackBarPreview() {
    NoostakAndroidTheme {
        NoostakSnackBar(message = "Noostak SnackBar")
    }
}
