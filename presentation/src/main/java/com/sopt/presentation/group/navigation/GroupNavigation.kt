package com.sopt.presentation.group.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.presentation.group.GroupRoute
import com.sopt.presentation.groupCreate.navigation.navigateToGroupCreate
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import com.sopt.presentation.groupEnter.navigation.navigateGroupEnter
import kotlinx.serialization.Serializable

fun NavController.navigateGroup(navOptions: NavOptions? = null) {
    navigate(
        route = Group,
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(0, inclusive = true)
            .build()
    )
}

fun NavGraphBuilder.groupNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavController
) {
    composable<Group> {
        GroupRoute(
            paddingValues = paddingValues,
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            },
            navigateToGroupCreate = { navHostController.navigateToGroupCreate() },
            navigateToGroupEnter = { navHostController.navigateGroupEnter() }
        )
    }
}

@Serializable
data object Group : MainTabRoute
