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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.Blue600
import com.sopt.core.designsystem.theme.Gray500
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.White
import com.sopt.presentation.R

@Composable
internal fun AuthButton(
    padding: PaddingValues,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    containerColor: Color = Blue600,
    contentColor: Color = White,
    isEnabled: Boolean = true,
    disabledContainerColor: Color = Gray500,
    disabledContentColor: Color = White,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        contentPadding = padding,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        )
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun AuthButtonPreview() {
    NoostakAndroidTheme {
        AuthButton(
            padding = PaddingValues(vertical = 13.dp),
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.btn_next),
                style = NoostakTheme.typography.b1SemiBold
            )
        }
    }
}