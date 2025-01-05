package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.NoRippleInteractionSource

@Composable
fun NoostakBottomButton(
    shape: Shape = RoundedCornerShape(8.dp),
    style: TextStyle = NoostakTheme.typography.t3Bold,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = dimensionResource(id = R.dimen.bottom_btn_vertical_padding),
    text: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    activateColor: Color = NoostakTheme.colors.blue600,
    deactivateColor: Color = NoostakTheme.colors.gray500,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.bottom_padding))
            .run {
                if (isEnabled) {
                    noRippleClickable(onClick = onButtonClick)
                } else {
                    this
                }
            },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) activateColor else deactivateColor
        ),
        contentPadding = PaddingValues(
            vertical = paddingVertical,
            horizontal = paddingHorizontal
        ),
        onClick = { onButtonClick() },
        enabled = isEnabled,
        interactionSource = NoRippleInteractionSource
    ) {
        Text(
            text = text,
            color = Color.White,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakBottomButtonPreview() {
    NoostakAndroidTheme {
        NoostakBottomButton(text = "다음", onButtonClick = {})
    }
}
