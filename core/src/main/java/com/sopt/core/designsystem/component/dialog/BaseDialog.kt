package com.sopt.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sopt.core.designsystem.theme.NoostakAndroidTheme

@Composable
fun BaseDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = true,
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    ),
    radius: Dp,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(radius)
                )
                .wrapContentSize()
        ) {
            content()
        }
    }
}

