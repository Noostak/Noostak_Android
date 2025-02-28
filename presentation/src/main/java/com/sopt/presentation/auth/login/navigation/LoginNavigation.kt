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
import com.sopt.presentation.group.navigation.navigateGroup
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Login,
        navOptions = navOptions
    )
}

fun NavController.navigateOnboarding(
    authId: String,
    socialType: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Onboarding(authId = authId, socialType = socialType),
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(Login, inclusive = false)
            .build()
    )
}

fun NavGraphBuilder.loginNavGraph(
    navHostController: NavController
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = { navHostController.navigateGroup() },
            navigateToOnboarding = { authId, socialType ->
                navHostController.navigateOnboarding(authId, socialType.toString())
            }
        )
    }

    composable<Onboarding> {
        val args = it.toRoute<Onboarding>()

        OnboardingRoute(
            authId = args.authId,
            socialType = args.socialType,
            navigateToSignUp = { authId, socialType ->
                navHostController.navigateSignUp(authId, socialType.toString())
            }
        )
    }
}

@Serializable
data object Login : Route

@Serializable
data class Onboarding(
    val authId: String,
    val socialType: String
) : Route
