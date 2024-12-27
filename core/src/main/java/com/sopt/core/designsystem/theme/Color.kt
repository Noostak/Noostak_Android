package com.sopt.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// Uncategorized
val Black = Color(0xFF111111)
val White = Color(0xFFFFFFFF)
val Red01 = Color(0xFF9C0303)
val Red02 = Color(0xFFFF6868)
val Pink = Color(0xFFFFCACA)

// Gray Scale
val Gray50 = Color(0xFFF8F8FA)
val Gray100 = Color(0xFFEFF1F3)
val Gray200 = Color(0xFFE9E9EB)
val Gray500 = Color(0xFFB1B1B1)
val Gray600 = Color(0xFF999999)
val Gray700 = Color(0xFF808080)
val Gray800 = Color(0xFF333333)
val Gray900 = Color(0xFF222222)

// Chip Color
val Blue = Color(0xFF3E8EFF)
val Purple = Color(0xFF8D78D8)
val Orange = Color(0xFFFF7826)
val Mint = Color(0xFFA9DBBE)

// Blue Scale
val Blue50 = Color(0xFFF0F4FF)
val Blue100 = Color(0xFFECF0FF)
val Blue200 = Color(0xFFCDD9FF)
val Blue300 = Color(0xFFB4C4FF)
val Blue400 = Color(0xFF9DAFE9)
val Blue500 = Color(0xFF7790ED)
val Blue600 = Color(0xFF7288CF)
val Blue700 = Color(0xFF7288CF)
val Blue800 = Color(0xFF49567F)

@Stable
class NoostakColors(
    black: Color,
    white: Color,
    red01: Color,
    red02: Color,
    pink: Color,
    gray50: Color,
    gray100: Color,
    gray200: Color,
    gray500: Color,
    gray600: Color,
    gray700: Color,
    gray800: Color,
    gray900: Color,
    blue50: Color,
    blue100: Color,
    blue200: Color,
    blue300: Color,
    blue400: Color,
    blue500: Color,
    blue600: Color,
    blue700: Color,
    blue800: Color,
    blue: Color,
    purple: Color,
    orange: Color,
    mint: Color
) {
    var black by mutableStateOf(black)
        private set
    var white by mutableStateOf(white)
        private set
    var red01 by mutableStateOf(red01)
        private set
    var red02 by mutableStateOf(red02)
        private set
    var pink by mutableStateOf(pink)
        private set
    var gray50 by mutableStateOf(gray50)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray500 by mutableStateOf(gray500)
        private set
    var gray600 by mutableStateOf(gray600)
        private set
    var gray700 by mutableStateOf(gray700)
        private set
    var gray800 by mutableStateOf(gray800)
        private set
    var gray900 by mutableStateOf(gray900)
        private set
    var blue50 by mutableStateOf(blue50)
        private set
    var blue100 by mutableStateOf(blue100)
        private set
    var blue200 by mutableStateOf(blue200)
        private set
    var blue300 by mutableStateOf(blue300)
        private set
    var blue400 by mutableStateOf(blue400)
        private set
    var blue500 by mutableStateOf(blue500)
        private set
    var blue600 by mutableStateOf(blue600)
        private set
    var blue700 by mutableStateOf(blue700)
        private set
    var blue800 by mutableStateOf(blue800)
        private set
    var blue by mutableStateOf(blue)
        private set
    var purple by mutableStateOf(purple)
        private set
    var orange by mutableStateOf(orange)
        private set
    var mint by mutableStateOf(mint)
        private set

    fun copy(): NoostakColors = NoostakColors(
        black = black,
        white = white,
        red01 = red01,
        red02 = red02,
        pink = pink,
        gray50 = gray50,
        gray100 = gray100,
        gray200 = gray200,
        gray500 = gray500,
        gray600 = gray600,
        gray700 = gray700,
        gray800 = gray800,
        gray900 = gray900,
        blue50 = blue50,
        blue100 = blue100,
        blue200 = blue200,
        blue300 = blue300,
        blue400 = blue400,
        blue500 = blue500,
        blue600 = blue600,
        blue700 = blue700,
        blue800 = blue800,
        blue = blue,
        purple = purple,
        orange = orange,
        mint = mint
    )

    fun update(other: NoostakColors) {
        black = other.black
        white = other.white
        red01 = other.red01
        red02 = other.red02
        pink = other.pink
        gray50 = other.gray50
        gray100 = other.gray100
        gray200 = other.gray200
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        gray800 = other.gray800
        gray900 = other.gray900
        blue50 = other.blue50
        blue100 = other.blue100
        blue200 = other.blue200
        blue300 = other.blue300
        blue400 = other.blue400
        blue500 = other.blue500
        blue600 = other.blue600
        blue700 = other.blue700
        blue800 = other.blue800
        blue = other.blue
        purple = other.purple
        orange = other.orange
        mint = other.mint
    }
}

@Composable
fun noostakColors(
    black: Color = Black,
    white: Color = White,
    red01: Color = Red01,
    red02: Color = Red02,
    pink: Color = Pink,
    gray50: Color = Gray50,
    gray100: Color = Gray100,
    gray200: Color = Gray200,
    gray500: Color = Gray500,
    gray600: Color = Gray600,
    gray700: Color = Gray700,
    gray800: Color = Gray800,
    gray900: Color = Gray900,
    blue50: Color = Blue50,
    blue100: Color = Blue100,
    blue200: Color = Blue200,
    blue300: Color = Blue300,
    blue400: Color = Blue400,
    blue500: Color = Blue500,
    blue600: Color = Blue600,
    blue700: Color = Blue700,
    blue800: Color = Blue800,
    blue: Color = Blue,
    purple: Color = Purple,
    orange: Color = Orange,
    mint: Color = Mint
) = NoostakColors(
    black = black,
    white = white,
    red01 = red01,
    red02 = red02,
    pink = pink,
    gray50 = gray50,
    gray100 = gray100,
    gray200 = gray200,
    gray500 = gray500,
    gray600 = gray600,
    gray700 = gray700,
    gray800 = gray800,
    gray900 = gray900,
    blue50 = blue50,
    blue100 = blue100,
    blue200 = blue200,
    blue300 = blue300,
    blue400 = blue400,
    blue500 = blue500,
    blue600 = blue600,
    blue700 = blue700,
    blue800 = blue800,
    blue = blue,
    purple = purple,
    orange = orange,
    mint = mint
)
