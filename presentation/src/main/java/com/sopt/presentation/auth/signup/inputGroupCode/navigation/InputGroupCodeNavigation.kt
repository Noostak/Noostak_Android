package com.sopt.presentation.auth.signup.inputGroupCode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.signup.inputGroupCode.InputGroupCodeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateInputGroupCode(navOptions: NavOptions? = null) {
    navigate(
        route = InputGroupCode,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.inputGroupCodeNavGraph(
    navigateUp: () -> Unit,
    navigateHome: () -> Unit
) {
    composable<InputGroupCode> {
        InputGroupCodeRoute(
            navigateUp = navigateUp,
            navigateToHome = navigateHome
        )
    }
}

@Serializable
data object InputGroupCode : Route