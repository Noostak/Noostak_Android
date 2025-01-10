package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakButton(
    text: String,
    textColor: Color,
    buttonColor: Color,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor.copy(alpha = 0.5f) // 비활성화 상태 색상
        ),
        onClick = { onButtonClick() },
        enabled = isEnabled
    ) {
        Text(
            text = text,
            color = textColor,
            style = NoostakTheme.typography.t3Bold
        )
    }
}
