package com.sopt.presentation.groupCreate.groupCreateSuccess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.groupCreate.navigation.GroupCreate
import com.sopt.presentation.groupCreate.groupCreateSuccess.GroupCreateSuccessRoute
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateToGroupCreateSuccess(navOptions: NavOptions? = null) {
    navigate(
        route = GroupCreateSuccess,
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(GroupCreate, inclusive = true)
            .build()
    )
}

fun NavGraphBuilder.groupCreateSuccessNavGraph(
    navHostController: NavController
) {
    composable<GroupCreateSuccess> {
        GroupCreateSuccessRoute(
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            },
        )
    }
}

@Serializable
data object GroupCreateSuccess : Route