package com.sopt.presentation.group.groupCreateSuccess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.group.groupCreateSuccess.GroupCreateSuccessRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToGroupCreateSuccess(navOptions: NavOptions? = null) {
    navigate(
        route = GroupCreateSuccess,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.groupCreateSuccessNavGraph(
    navHostController: NavController
) {
    composable<GroupCreateSuccess> {
        GroupCreateSuccessRoute()
    }
}

@Serializable
data object GroupCreateSuccess : Route