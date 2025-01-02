package com.sopt.presentation.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignUp(
    authId: String,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = SignUp(authId = authId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.signUpNavGraph(
    navigateToCheckInvite: (String) -> Unit
) {
    composable<SignUp> {
        val args = it.toRoute<SignUp>()
        SignUpRoute(
            authId = args.authId,
            navigateToCheckInvite = { navigateToCheckInvite(args.authId) }
        )
    }
}

@Serializable
data class SignUp(
    val authId: String
) : Route