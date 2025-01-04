package com.sopt.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.NoRippleInteractionSource

@Composable
fun AppointmentDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    description: String,
    dismissText: String,
    confirmButtonText: String
) {
    BaseDialog(
        onDismissRequest = onDismissRequest,
        radius = 20.dp
    ) {
        Column(
            modifier = Modifier.padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(53.dp)
                    .background(
                        shape = CircleShape,
                        color = NoostakTheme.colors.gray200
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.b2Regular,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NoostakTheme.colors.gray700
                ),
                shape = RoundedCornerShape(6.dp),
                onClick = onConfirmButtonClick,
                interactionSource = NoRippleInteractionSource,
            ) {
                Text(
                    text = confirmButtonText,
                    color = NoostakTheme.colors.white,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(6.5.dp))
            Text(
                modifier = Modifier.noRippleClickable { onDismissRequest() },
                text = dismissText,
                color = NoostakTheme.colors.gray700,
                style = NoostakTheme.typography.b2Regular,
            )
        }
    }
}
