package com.sopt.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.sopt.core.extension.SetNavigationBarColor
import com.sopt.core.extension.SetStatusBarColor

private val LightColorScheme = lightColorScheme(
    primary = Blue600,
    background = White
)

private val DarkColorScheme = lightColorScheme(
    background = White
)

private val LocalNoostakTypography = staticCompositionLocalOf<NoostakTypography> {
    error("NoostakTypography not provided")
}

private val LocalNoostakColors = staticCompositionLocalOf<NoostakColors> {
    error("NoostakColors not provided")
}

/* NoostakTheme
*
* Typo를 변경하고 싶다면 NoostakTheme.typography.h1Bold으로 접근하시면 됩니다.
* ex) Text(text = "Noostak Example Typo", style = NoostakTheme.typography.h1Bold)
*
* Color를 변경하고 싶다면 NoostakTheme.colors.gray800으로 접근하시면 됩니다.
* ex) Text(text = "Noostak Example Color", color = NoostakTheme.colors.gray800)
*/
object NoostakTheme {
    val typography: NoostakTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalNoostakTypography.current

    val colors: NoostakColors
        @Composable
        @ReadOnlyComposable
        get() = LocalNoostakColors.current
}

@Composable
fun ProvideNoostakColorsAndTypography(colors: NoostakColors, typography: NoostakTypography, content: @Composable () -> Unit) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)

    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)

    CompositionLocalProvider(
        LocalNoostakColors provides provideColors,
        LocalNoostakTypography provides provideTypography,
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
    val colors = noostakColors()
    val typography = noostakTypography()
    SetStatusBarColor(color = colors.white)
    SetNavigationBarColor(color = colors.white)
    ProvideNoostakColorsAndTypography(colors, typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
