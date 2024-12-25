package com.sopt.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sopt.core.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal))
val PretendardSemiBold =
    FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal))
val PretendardMedium =
    FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal))
val PretendardRegular =
    FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal))

@Stable
class NoostakTypography internal constructor(
    codeMedium: TextStyle,
    h1Bold: TextStyle,
    h1SemiBold: TextStyle,
    h2Bold: TextStyle,
    h3SemiBold: TextStyle,
    h4Bold: TextStyle,
    h4SemiBold: TextStyle,
    h5Bold: TextStyle,
    t1SemiBold: TextStyle,
    t2Regular: TextStyle,
    t3Bold: TextStyle,
    t4Bold: TextStyle,
    b1SemiBold: TextStyle,
    b2Regular: TextStyle,
    b4SemiBold: TextStyle,
    b4SemiBold1: TextStyle,
    b4Regular: TextStyle,
    b5Regular: TextStyle,
    c1Bold: TextStyle,
    c2SemiBold: TextStyle,
    c3Regular: TextStyle,
    c4Regular: TextStyle
) {
    var codeMedium: TextStyle by mutableStateOf(codeMedium)
        private set
    var h1Bold: TextStyle by mutableStateOf(h1Bold)
        private set
    var h1SemiBold: TextStyle by mutableStateOf(h1SemiBold)
        private set
    var h2Bold: TextStyle by mutableStateOf(h2Bold)
        private set
    var h3SemiBold: TextStyle by mutableStateOf(h3SemiBold)
        private set
    var h4Bold: TextStyle by mutableStateOf(h4Bold)
        private set
    var h4SemiBold: TextStyle by mutableStateOf(h4SemiBold)
        private set
    var h5Bold: TextStyle by mutableStateOf(h5Bold)
        private set
    var t1SemiBold: TextStyle by mutableStateOf(t1SemiBold)
        private set
    var t2Regular: TextStyle by mutableStateOf(t2Regular)
        private set
    var t3Bold: TextStyle by mutableStateOf(t3Bold)
        private set
    var t4Bold: TextStyle by mutableStateOf(t4Bold)
        private set
    var b1SemiBold: TextStyle by mutableStateOf(b1SemiBold)
        private set
    var b2Regular: TextStyle by mutableStateOf(b2Regular)
        private set
    var b4SemiBold: TextStyle by mutableStateOf(b4SemiBold)
        private set
    var b4SemiBold1: TextStyle by mutableStateOf(b4SemiBold1)
        private set
    var b4Regular: TextStyle by mutableStateOf(b4Regular)
        private set
    var b5Regular: TextStyle by mutableStateOf(b5Regular)
        private set
    var c1Bold: TextStyle by mutableStateOf(c1Bold)
        private set
    var c2SemiBold: TextStyle by mutableStateOf(c2SemiBold)
        private set
    var c3Regular: TextStyle by mutableStateOf(c3Regular)
        private set
    var c4Regular: TextStyle by mutableStateOf(c4Regular)
        private set

    fun copy(
        codeMedium: TextStyle = this.codeMedium,
        h1Bold: TextStyle = this.h1Bold,
        h1SemiBold: TextStyle = this.h1SemiBold,
        h2Bold: TextStyle = this.h2Bold,
        h3SemiBold: TextStyle = this.h3SemiBold,
        h4Bold: TextStyle = this.h4Bold,
        h4SemiBold: TextStyle = this.h4SemiBold,
        h5Bold: TextStyle = this.h5Bold,
        t1SemiBold: TextStyle = this.t1SemiBold,
        t2Regular: TextStyle = this.t2Regular,
        t3Bold: TextStyle = this.t3Bold,
        t4Bold: TextStyle = this.t4Bold,
        b1SemiBold: TextStyle = this.b1SemiBold,
        b2Regular: TextStyle = this.b2Regular,
        b4SemiBold: TextStyle = this.b4SemiBold,
        b4SemiBold1: TextStyle = this.b4SemiBold1,
        b4Regular: TextStyle = this.b4Regular,
        b5Regular: TextStyle = this.b5Regular,
        c1Bold: TextStyle = this.c1Bold,
        c2SemiBold: TextStyle = this.c2SemiBold,
        c3Regular: TextStyle = this.c3Regular,
        c4Regular: TextStyle = this.c4Regular
    ): NoostakTypography = NoostakTypography(
        codeMedium = codeMedium,
        h1Bold = h1Bold,
        h1SemiBold = h1SemiBold,
        h2Bold = h2Bold,
        h3SemiBold = h3SemiBold,
        h4Bold = h4Bold,
        h4SemiBold = h4SemiBold,
        h5Bold = h5Bold,
        t1SemiBold = t1SemiBold,
        t2Regular = t2Regular,
        t3Bold = t3Bold,
        t4Bold = t4Bold,
        b1SemiBold = b1SemiBold,
        b2Regular = b2Regular,
        b4SemiBold = b4SemiBold,
        b4SemiBold1 = b4SemiBold1,
        b4Regular = b4Regular,
        b5Regular = b5Regular,
        c1Bold = c1Bold,
        c2SemiBold = c2SemiBold,
        c3Regular = c3Regular,
        c4Regular = c4Regular
    )

    fun update(other: NoostakTypography) {
        codeMedium = other.codeMedium
        h1Bold = other.h1Bold
        h1SemiBold = other.h1SemiBold
        h2Bold = other.h2Bold
        h3SemiBold = other.h3SemiBold
        h4Bold = other.h4Bold
        h4SemiBold = other.h4SemiBold
        h5Bold = other.h5Bold
        t1SemiBold = other.t1SemiBold
        t2Regular = other.t2Regular
        t3Bold = other.t3Bold
        t4Bold = other.t4Bold
        b1SemiBold = other.b1SemiBold
        b2Regular = other.b2Regular
        b4SemiBold = other.b4SemiBold
        b4SemiBold1 = other.b4SemiBold1
        b4Regular = other.b4Regular
        b5Regular = other.b5Regular
        c1Bold = other.c1Bold
        c2SemiBold = other.c2SemiBold
        c3Regular = other.c3Regular
        c4Regular = other.c4Regular
    }
}

fun noostakTextStyle(
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit = 0.sp
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

@Composable
fun noostakTypography(): NoostakTypography {
    return NoostakTypography(
        codeMedium = noostakTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 56.sp,
            lineHeight = 67.sp,
            letterSpacing = 13.sp
        ),
        h1Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp,
            lineHeight = 38.sp
        ),
        h1SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 27.sp,
            lineHeight = 38.sp
        ),
        h2Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 34.sp
        ),
        h3SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 34.sp
        ),
        h4Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        h4SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        h5Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 25.sp
        ),
        t1SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 25.sp
        ),
        t2Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 25.sp
        ),
        t3Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 24.sp
        ),
        t4Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        b1SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        b2Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        b4SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = 21.sp
        ),
        b4SemiBold1 = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.15.sp
        ),
        b4Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 21.sp
        ),
        b5Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        c1Bold = noostakTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 18.sp
        ),
        c2SemiBold = noostakTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 18.sp
        ),
        c3Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.sp
        ),
        c4Regular = noostakTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 15.sp
        )
    )
}
