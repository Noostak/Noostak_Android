package com.sopt.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.DialogType
import com.sopt.core.util.NoRippleInteractionSource

@Composable
fun NoostakDialog(
    dialogType: DialogType,
    onClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_radius))
        ) {
            Column(
                modifier = Modifier
                    .width(274.dp)
                    .wrapContentHeight()
                    .background(
                        color = NoostakTheme.colors.white
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.height(
                        when (dialogType) {
                            DialogType.LOGOUT -> 41.dp
                            DialogType.APPOINTMENT, DialogType.WITHDRAWAL, DialogType.DATA_FAILURE, DialogType.NETWORK_FAILURE -> 30.dp
                            else -> 24.dp
                        }
                    )
                )
                Text(
                    text = stringResource(dialogType.content),
                    textAlign = TextAlign.Center,
                    style = when (dialogType) {
                        DialogType.LOGOUT, DialogType.WITHDRAWAL, DialogType.APPOINTMENT, DialogType.DATA_FAILURE, DialogType.NETWORK_FAILURE -> NoostakTheme.typography.b4Regular
                        else -> NoostakTheme.typography.c3Regular
                    }
                )
                Spacer(
                    modifier = Modifier.height(
                        when (dialogType) {
                            DialogType.LOGOUT -> 36.dp
                            DialogType.APPOINTMENT, DialogType.WITHDRAWAL, DialogType.DATA_FAILURE, DialogType.NETWORK_FAILURE -> 26.dp
                            else -> 20.dp
                        }
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = NoostakTheme.colors.gray200,
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min) // Row의 높이를 내부 컴포넌트에 맞춤
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NoostakTheme.colors.white,
                            contentColor = NoostakTheme.colors.gray900,
                            disabledContainerColor = NoostakTheme.colors.white,
                            disabledContentColor = NoostakTheme.colors.gray900
                        ),
                        interactionSource = NoRippleInteractionSource
                    ) {
                        Text(
                            text = stringResource(dialogType.dismissText),
                            textAlign = TextAlign.Center,
                            style = NoostakTheme.typography.c3SemiBold
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(),
                        color = NoostakTheme.colors.gray200,
                        thickness = 1.dp
                    )

                    Button(
                        onClick = { onClick() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NoostakTheme.colors.white,
                            contentColor = NoostakTheme.colors.gray900,
                            disabledContainerColor = NoostakTheme.colors.white,
                            disabledContentColor = NoostakTheme.colors.gray900
                        ),
                        interactionSource = NoRippleInteractionSource
                    ) {
                        Text(
                            text = stringResource(dialogType.confirmText),
                            textAlign = TextAlign.Center,
                            style = NoostakTheme.typography.c3SemiBold,
                            color = NoostakTheme.colors.blue
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoostakDialogPreview() {
    NoostakAndroidTheme {
        NoostakDialog(dialogType = DialogType.APPOINTMENT, onClick = {}, onDismissRequest = {})
    }
}
