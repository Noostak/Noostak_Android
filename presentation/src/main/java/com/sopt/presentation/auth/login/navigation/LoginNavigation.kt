package com.sopt.presentation.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateLogin(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Login,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.loginNavGraph(
    navigateHome: () -> Unit,
    navigateSignUp: (String) -> Unit
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = navigateHome,
            navigateToSignUp = navigateSignUp
        )
    }
}

@Serializable
data object Login : Route