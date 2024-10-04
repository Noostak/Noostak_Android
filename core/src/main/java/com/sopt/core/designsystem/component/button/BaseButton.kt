package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.util.NoRippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseButton(
    shape: Shape,
    style: TextStyle,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp,
    text: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides NoRippleConfiguration) {
        Button(
            modifier = modifier,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
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