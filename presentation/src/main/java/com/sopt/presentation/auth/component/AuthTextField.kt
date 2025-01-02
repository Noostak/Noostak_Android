package com.sopt.presentation.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.Gray500
import com.sopt.core.designsystem.theme.Gray800
import com.sopt.core.designsystem.theme.Gray900
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.showIf
import com.sopt.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    maxLength: Int,
    exampleText: String,
    isExampleVisible: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onTextChange(it)
                }
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    style = NoostakTheme.typography.b5Regular.copy(color = Color.Gray)
                )
            },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gray900,
                unfocusedBorderColor = Gray500
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exampleText,
                style = NoostakTheme.typography.b5Regular.copy(color = Gray800),
                modifier = Modifier.showIf(isExampleVisible)
            )
            Text(
                stringResource(R.string.tv_signup_count, text.length, maxLength),
                style = NoostakTheme.typography.b5Regular.copy(color = Gray800)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldWithLimitPreview() {
    var text by remember { mutableStateOf("") }

    NoostakAndroidTheme {
        AuthTextField(
            text = text,
            onTextChange = { text = it },
            placeholderText = stringResource(R.string.hint_signup_name),
            maxLength = 10,
            exampleText = stringResource(R.string.tv_signup_example_name),
            isExampleVisible = true
        )
    }
}