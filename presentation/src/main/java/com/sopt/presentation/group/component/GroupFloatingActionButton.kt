package com.sopt.presentation.group.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.presentation.R

@Composable
fun GroupFloatingActionButton(
    onClick: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier.background(
                color = NoostakTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 17.dp, vertical = 18.dp)) {
                GroupFloatingActionButtonItem(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    text = stringResource(R.string.text_group_create_title),
                ) {
                    // nav to group create page
                }
                Spacer(modifier = Modifier.height(12.dp))
                GroupFloatingActionButtonItem(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    text = stringResource(R.string.text_group_fab_enter)
                ) {
                    // nav to group enter page
                }
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        FloatingActionButton(
            modifier = Modifier.align(Alignment.End),
            shape = CircleShape,
            containerColor = NoostakTheme.colors.white,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = { onClick() },
            interactionSource = NoRippleInteractionSource
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_fab_close),
                contentDescription = null,
                tint = NoostakTheme.colors.black
            )
        }
        Spacer(modifier = Modifier.height(73.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GroupFloatingActionButtonPreview() {
    NoostakAndroidTheme {
        GroupFloatingActionButton(onClick = {})
    }
}