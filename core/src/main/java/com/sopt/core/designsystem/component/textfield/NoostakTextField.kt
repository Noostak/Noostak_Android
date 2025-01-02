package com.sopt.core.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState

@Composable
fun NoostakTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = NoostakTheme.colors.gray500,
                style = NoostakTheme.typography.b1SemiBold
            )
        },
        enabled = isEnabled,
        modifier = modifier
            .height(52.dp)
            .border(
                width = 1.dp,
                color = if (isFocused) NoostakTheme.colors.blue300 else NoostakTheme.colors.gray200,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = NoostakTheme.typography.b1SemiBold,
        singleLine = true,
        interactionSource = interactionSource
    )
}
