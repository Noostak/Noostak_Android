package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun NoostakButton(
    modifier: Modifier = Modifier,
    text: String,
    onButtonClick: () -> Unit,
    isEnabled: Boolean
) {
    BaseButton(
        modifier = modifier.fillMaxWidth(),
        isEnabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        style = NoostakTheme.typography.t3Bold,
        paddingVertical = 15.dp,
        text = text,
        onButtonClick = { onButtonClick() }
    )
}
