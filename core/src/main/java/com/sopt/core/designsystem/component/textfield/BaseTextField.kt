package com.sopt.core.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.core.designsystem.theme.NoostakAndroidTheme

@Composable
fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isError: Boolean,
    errorMessage: String,
    isEnabled: Boolean = true,
    modifier: Modifier
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray
            )
        },
        enabled = isEnabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
fun BaseTextFieldPreview() {
    NoostakAndroidTheme {
        BaseTextField(
            value = "Value",
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            isError = false,
            errorMessage = "Error message",
            modifier = Modifier
        )
    }
}
