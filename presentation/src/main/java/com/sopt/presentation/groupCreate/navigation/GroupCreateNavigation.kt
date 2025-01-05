package com.sopt.presentation.groupCreate.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.Route
import com.sopt.presentation.groupCreate.GroupCreateRoute
import com.sopt.presentation.groupCreate.groupCreateSuccess.navigation.navigateToGroupCreateSuccess
import kotlinx.serialization.Serializable

fun NavController.navigateToGroupCreate(navOptions: NavOptions? = null) {
    navigate(
        route = GroupCreate,
        navOptions = navOptions
    )
}

fun NavController.navigateToGroupEnter(navOptions: NavOptions? = null) {
    navigate(
        route = GroupEnter,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.groupCreateNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavController
) {
    composable<GroupCreate> {
        GroupCreateRoute(
            paddingValues = paddingValues,
            navigateToGroupCreateSuccess = {
                navHostController.navigateToGroupCreateSuccess()
            }
        )
    }

    composable<GroupEnter> {
        // 이동 코드 추가하기
    }
}

@Serializable
data object GroupCreate : Route

@Serializable
data object GroupEnter : Route
