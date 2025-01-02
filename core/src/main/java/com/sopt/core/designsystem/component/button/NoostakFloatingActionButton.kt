package com.sopt.core.designsystem.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource

@Composable
fun NoostakFloatingActionButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.fab_radius)),
        containerColor = NoostakTheme.colors.black,
        contentColor = NoostakTheme.colors.white,
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        onClick = { onClick() },
        interactionSource = NoRippleInteractionSource
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
            contentDescription = null,
            tint = NoostakTheme.colors.white
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = title,
            color = NoostakTheme.colors.white,
            style = NoostakTheme.typography.b4SemiBold1
        )
    }
}
