package com.sopt.core.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.TextFieldType

@Composable
fun NoostakTextField(
    textFieldType: TextFieldType,
    value: String = "",
    onValueChange: (String) -> Unit = { _ -> },
    placeholderColor: Color = NoostakTheme.colors.gray600,
    textStyle: TextStyle = NoostakTheme.typography.b5Regular,
    shape: Shape = RoundedCornerShape(6.dp),
    cursorColor: Color = NoostakTheme.colors.gray600,
    focusedBorderColor: Color = NoostakTheme.colors.gray900,
    unfocusedWithInputBorderColor: Color = NoostakTheme.colors.gray700,
    unfocusedBorderColor: Color = NoostakTheme.colors.gray500,
    maxLength: Int = 30,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = when {
                        isFocused -> focusedBorderColor // 포커스된 경우
                        value.isNotEmpty() -> unfocusedWithInputBorderColor // 텍스트가 입력되고 포커스 안된 경우
                        else -> unfocusedBorderColor // 포커스되지 않은 경우
                    }, shape = shape
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = value,
                    textStyle = textStyle,
                    onValueChange = { newValue ->
                        if (newValue.replace(" ", "").length <= maxLength) onValueChange(
                            newValue
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = textFieldType.placeholder),
                            color = placeholderColor,
                            style = textStyle,
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    colors = TextFieldDefaults.colors(
                        cursorColor = cursorColor,
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions
                )

                if (textFieldType != TextFieldType.CALENDAR && value.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp),
                        onClick = { onValueChange("") },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_text_field_delete),
                            contentDescription = stringResource(R.string.icon_noostak_text_field_descrition),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (textFieldType == TextFieldType.SIGNUP) stringResource(R.string.text_noostak_text_field_sign_up_example) else "",
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.b5Regular,
                modifier = modifier.padding(top = 6.dp),
                maxLines = 1
            )

            Text(
                text = stringResource(
                    R.string.text_noostak_text_field_count,
                    value.length,
                    maxLength
                ),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.b5Regular,
                modifier = modifier.padding(top = 6.dp),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakTextFieldPreview() {
    NoostakAndroidTheme {
        Column {
            NoostakTextField(textFieldType = TextFieldType.GROUP, value = "누스탁")
        }
    }
}
