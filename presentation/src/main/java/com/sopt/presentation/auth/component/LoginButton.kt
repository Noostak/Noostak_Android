package com.sopt.presentation.auth.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R

@Composable
internal fun LoginButton(
    text: String,
    style: TextStyle = NoostakTheme.typography.b1SemiBold,
    onLoginBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    containerColor: Color = NoostakTheme.colors.yellow,
    contentColor: Color = NoostakTheme.colors.black,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = onLoginBtnClick,
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 13.dp),
        shape = shape,
        modifier = modifier
            .noRippleClickable(onClick = onLoginBtnClick)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthButtonPreview() {
    NoostakAndroidTheme {
        LoginButton(
            onLoginBtnClick = { },
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.btn_login_kakao)
        )
    }
}