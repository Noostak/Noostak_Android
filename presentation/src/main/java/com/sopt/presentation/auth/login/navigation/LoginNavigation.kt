package com.sopt.presentation.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.LoginRoute
import com.sopt.presentation.auth.login.OnboardingRoute
import com.sopt.presentation.auth.signup.navigation.navigateSignUp
import com.sopt.presentation.auth.splash.SplashRoute
import com.sopt.presentation.group.navigation.navigateGroup
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Login,
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(0, inclusive = false)
            .build()
    )
}

fun NavController.navigateOnboarding(
    authId: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Onboarding(authId = authId),
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(Login, inclusive = false)
            .build()
    )
}

fun NavGraphBuilder.loginNavGraph(
    navHostController: NavController
) {
    composable<Splash> {
        SplashRoute(
            navigateToLogin = { navHostController.navigateToLogin() },
            navigateToHome = { navHostController.navigateGroup() }
        )
    }

    composable<Login> {
        LoginRoute(
            navigateToHome = { navHostController.navigateGroup() },
            navigateToOnboarding = { authId ->
                navHostController.navigateOnboarding(authId)
            }
        )
    }

    composable<Onboarding> {
        val args = it.toRoute<Onboarding>()

        OnboardingRoute(
            authId = args.authId,
            navigateToSignUp = { authId ->
                navHostController.navigateSignUp(authId)
            }
        )
    }
}

@Serializable
data object Splash : Route

@Serializable
data object Login : Route

@Serializable
data class Onboarding(
    val authId: String
) : Route
