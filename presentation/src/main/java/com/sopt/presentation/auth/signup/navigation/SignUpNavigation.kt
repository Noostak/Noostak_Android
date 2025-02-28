package com.sopt.presentation.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.navigation.Login
import com.sopt.presentation.auth.signup.SignUpRoute
import com.sopt.presentation.auth.signup.checkInvite.navigation.navigateCheckInvite
import kotlinx.serialization.Serializable

fun NavController.navigateSignUp(
    authId: String,
    socialType: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = SignUp(authId = authId, socialType = socialType),
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(Login, inclusive = false)
            .build()
    )
}

fun NavGraphBuilder.signUpNavGraph(
    navHostController: NavController
) {
    composable<SignUp> {
        val args = it.toRoute<SignUp>()
        SignUpRoute(
            authId = args.authId,
            socialType = args.socialType,
            navigateToCheckInvite = { name ->
                navHostController.navigateCheckInvite(name)
            }
        )
    }
}

@Serializable
data class SignUp(
    val authId: String,
    val socialType: String
) : Route
