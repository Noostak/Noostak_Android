package com.sopt.presentation.auth.splash

sealed class SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect()
    data object NavigateToHome : SplashSideEffect()
}