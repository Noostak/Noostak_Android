package com.sopt.presentation.groupCreate.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun GroupProfileNameTextField(
    value: String = "",
    onValueChange: (String) -> Unit = { _ -> },
    placeholder: String = "",
    placeholderColor: Color = NoostakTheme.colors.gray600,
    textStyle: TextStyle = NoostakTheme.typography.b5Regular,
    shape: Shape = RoundedCornerShape(6.dp),
    focusedBorderColor: Color = NoostakTheme.colors.gray900,
    unfocusedBorderColor: Color = NoostakTheme.colors.gray500,
    maxLength: Int = 30,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column {
        OutlinedTextField(
            value = value,
            textStyle = textStyle,
            placeholder = { Text(text = placeholder, color = placeholderColor, style = textStyle) },
            onValueChange = { newValue ->
                if (newValue.replace(" ", "").length <= maxLength) onValueChange(newValue)
            },
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
            ),
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_group_create_delete),
                            contentDescription = "Delete Group Profile Name",
                            tint = Color.Unspecified
                        )
                    }
                }
            },
            modifier = modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        Text(
            text = "${value.length}/$maxLength",
            color = NoostakTheme.colors.gray800,
            style = NoostakTheme.typography.b5Regular,
            modifier = modifier
                .align(Alignment.End)
                .padding(top = 6.dp),
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupProfileNameTextFieldPreview() {
    NoostakAndroidTheme {
        GroupProfileNameTextField(placeholder = "그룹명을 입력해주세요", value = "누스탁")
    }
}