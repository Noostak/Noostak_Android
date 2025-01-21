package com.sopt.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun TimeTextField(
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val isTimeExceedLimit = (text.text.toIntOrNull() ?: 0) > 10

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                if (newValue.text.length <= 2) {
                    text = newValue
                    onValueChange(newValue.text)
                }
            },
            textStyle = NoostakTheme.typography.b1SemiBold.copy(textAlign = TextAlign.Right),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 44.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            thickness = 2.dp,
            color = if (isTimeExceedLimit) {
                NoostakTheme.colors.red02
            } else if (isFocused) {
                NoostakTheme.colors.blue600
            } else {
                NoostakTheme.colors.gray500
            }
        )
        Text(
            text = stringResource(R.string.text_time_text_field),
            style = NoostakTheme.typography.c2SemiBold,
            color = if (isTimeExceedLimit) NoostakTheme.colors.red02 else NoostakTheme.colors.gray500,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}
