package com.sopt.presentation.auth.signup.checkInvite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.auth.signup.checkInvite.CheckInviteRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCheckInvite(navOptions: NavOptions? = null) {
    navigate(
        route = CheckInvite,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.checkInviteNavGraph(
    navigateToGroup: () -> Unit,
    navigateToInputGroupCode: () -> Unit,
) {
    composable<CheckInvite> {
        CheckInviteRoute(
            navigateToGroup = navigateToGroup,
            navigateToInputGroupCode = navigateToInputGroupCode,
        )
    }
}

@Serializable
data object CheckInvite : Route