package com.sopt.presentation.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.LoginRoute
import com.sopt.presentation.auth.signup.navigation.navigateSignUp
import com.sopt.presentation.group.navigation.navigateGroup
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
    navHostController: NavController
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = { navHostController.navigateGroup() },
            navigateToSignUp = { authId ->
                navHostController.navigateSignUp(authId)
            }
        )
    }
}

@Serializable
data object Login : Route