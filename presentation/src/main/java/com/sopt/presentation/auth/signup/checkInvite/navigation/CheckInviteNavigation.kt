package com.sopt.presentation.auth.signup.checkInvite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.signup.checkInvite.CheckInviteRoute
import com.sopt.presentation.auth.signup.inputGroupCode.navigation.navigateInputGroupCode
import com.sopt.presentation.group.navigation.navigateGroup
import kotlinx.serialization.Serializable

fun NavController.navigateCheckInvite(
    name: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CheckInvite(name = name),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.checkInviteNavGraph(
    navHostController: NavController
) {
    composable<CheckInvite> {
        val args = it.toRoute<CheckInvite>()
        CheckInviteRoute(
            name = args.name,
            navigateToGroup = { navHostController.navigateGroup() },
            navigateToInputGroupCode = { navHostController.navigateInputGroupCode() }
        )
    }
}

@Serializable
data class CheckInvite(
    val name: String
) : Route
