package com.sopt.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    background = Color.White
)

private val DarkColorScheme = lightColorScheme(
    background = Color.White
)

private val LocalNoostakTypography = staticCompositionLocalOf<NoostakTypography> {
    error("NoostakTypography not provided")
}

/* NoostakTheme
*
* Typo를 변경하고 싶다면 NoostakTheme.typography.h1Bold으로 접근하시면 됩니다.
* ex) Text(text = "Noostak Example Typo", style = NoostakTheme.typography.h1Bold)
*/
object NoostakTheme {
    val typography: NoostakTypography
        @Composable
        get() = LocalNoostakTypography.current
}

@Composable
fun ProvideNoostakTypography(typography: NoostakTypography, content: @Composable () -> Unit) {
    val provideTypography = remember {
        typography.copy()
    }
    provideTypography.update(typography)
    CompositionLocalProvider(
        value = LocalNoostakTypography provides provideTypography,
        content = content
    )
}

@Composable
fun NoostakAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val typography = noostakTypography()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    ProvideNoostakTypography(typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
