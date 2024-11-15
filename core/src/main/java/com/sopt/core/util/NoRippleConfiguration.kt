package com.sopt.core.util

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
val NoRippleConfiguration = RippleConfiguration(
    color = Color.Unspecified,
    rippleAlpha = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.0f
    )
)