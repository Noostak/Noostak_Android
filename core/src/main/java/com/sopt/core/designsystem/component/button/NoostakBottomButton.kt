package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.NoRippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoostakBottomButton(
    shape: Shape = RoundedCornerShape(8.dp),
    style: TextStyle = NoostakTheme.typography.t3Bold,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 15.dp,
    text: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    activateColor: Color = NoostakTheme.colors.blue600,
    deactivateColor: Color = NoostakTheme.colors.gray500,
    isEnabled: Boolean = true
) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides NoRippleConfiguration) {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .run {
                    if (isEnabled) noRippleClickable(onClick = onButtonClick)
                    else this
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
            enabled = isEnabled
        ) {
            Text(
                text = text,
                color = Color.White,
                style = style
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakBottomButtonPreview() {
    NoostakAndroidTheme {
        NoostakBottomButton(text = "다음", onButtonClick = {})
    }
}