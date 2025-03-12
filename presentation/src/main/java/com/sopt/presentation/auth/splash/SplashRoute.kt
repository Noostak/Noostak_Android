package com.sopt.presentation.auth.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.schetook_splash)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = true
    )

    LaunchedEffect(Unit) {
        delay(2000)
        splashViewModel.getIsAutoLogin()
        splashViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is SplashSideEffect.NavigateToHome -> navigateToHome()
                is SplashSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SplashScreen(
        composition = composition,
        progress = progress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SplashScreen(
    composition: LottieComposition?,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier.scale(1.5f)
        )
    }
}
