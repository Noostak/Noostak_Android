package com.sopt.core.type

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.dp
import com.sopt.core.R

enum class ProfileType(
    @DrawableRes val defaultImage: Int,
    val imageSize: androidx.compose.ui.unit.Dp
) {
    PROFILE(
        defaultImage = R.drawable.ic_profile_image,
        imageSize = 112.dp
    ),
    GROUP(
        defaultImage = R.drawable.ic_group_image,
        imageSize = 105.dp
    )
}
