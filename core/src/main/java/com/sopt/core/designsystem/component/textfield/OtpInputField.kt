package com.sopt.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val char = text.getOrNull(index)?.toString() ?: ""
    Text(
        modifier = Modifier
            .width(46.dp)
            .background(
                color = NoostakTheme.colors.gray100,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(13.dp),
        text = char,
        style = NoostakTheme.typography.h1SemiBold,
        color = NoostakTheme.colors.gray800,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOtpInputField() {
    var otpValue by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .padding(20.dp)
            .background(NoostakTheme.colors.white)
    ) {
        OtpInputField(
            otpText = otpValue,
            onOtpTextChange = { otp, isComplete ->
                otpValue = otp
                if (isComplete) {
                    println("OTP Completed: $otp")
                }
            }
        )
    }
}