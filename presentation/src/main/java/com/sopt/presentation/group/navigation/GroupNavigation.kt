package com.sopt.presentation.group.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.presentation.group.GroupRoute
import com.sopt.presentation.group.groupCreate.navigation.navigateToGroupCreate
import com.sopt.presentation.group.groupCreate.navigation.navigateToGroupEnter
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateGroup(navOptions: NavOptions? = null) {
    navigate(
        route = Group,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.groupNavGraph(
    navHostController: NavController
) {
    composable<Group> {
        GroupRoute(
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            },
            navigateToGroupCreate = { navHostController.navigateToGroupCreate() },
            navigateToGroupEnter = { navHostController.navigateToGroupEnter() }
        )
    }
}

@Serializable
data object Group : MainTabRoute
