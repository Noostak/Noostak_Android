package com.sopt.presentation.groupEnter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.login.navigation.Login
import com.sopt.presentation.group.navigation.navigateGroup
import com.sopt.presentation.groupEnter.GroupEnterRoute
import kotlinx.serialization.Serializable

fun NavController.navigateGroupEnter(
    navOptions: NavOptions? = null
) {
    navigate(
        route = GroupEnter,
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(Login, inclusive = false)
            .build()
    )
}

fun NavGraphBuilder.groupEnterNavGraph(
    navHostController: NavController
) {
    composable<GroupEnter> {
        GroupEnterRoute(
            navigateUp = { navHostController.navigateUp() },
            navigateToGroup = {
                navHostController.navigateGroup()
            }
        )
    }
}

@Serializable
data object GroupEnter : Route
