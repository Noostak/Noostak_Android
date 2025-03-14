package com.sopt.core.type

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sopt.core.R

enum class ImagePickerType(@DrawableRes val profileImage: Int, val shape: Shape, val size: Dp) {
    USER(R.drawable.ic_user_profile, CircleShape, 112.dp),
    GROUP(
        R.drawable.ic_group_profile,
        RoundedCornerShape(
            23.dp
        ),
        105.dp
    )
}
