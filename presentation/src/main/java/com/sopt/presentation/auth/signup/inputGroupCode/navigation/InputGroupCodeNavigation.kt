package com.sopt.presentation.auth.signup.inputGroupCode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.navigation.Login
import com.sopt.presentation.auth.signup.inputGroupCode.InputGroupCodeRoute
import com.sopt.presentation.group.navigation.navigateGroup
import kotlinx.serialization.Serializable

fun NavController.navigateInputGroupCode(
    navOptions: NavOptions? = null
) {
    navigate(
        route = InputGroupCode,
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(Login, inclusive = false)
            .build()
    )
}

fun NavGraphBuilder.inputGroupCodeNavGraph(
    navHostController: NavController
) {
    composable<InputGroupCode> {
        InputGroupCodeRoute(
            navigateUp = { navHostController.navigateUp() },
            navigateToGroup = {
                navHostController.popBackStack()
                navHostController.navigateGroup()
            }
        )
    }
}

@Serializable
data object InputGroupCode : Route
