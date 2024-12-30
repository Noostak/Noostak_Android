package com.sopt.presentation.groupDetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.groupDetail.GroupDetailRoute
import com.sopt.presentation.groupDetail.screen.CompleteDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateGroupDetail(
    id: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = GroupDetail(id = id),
        navOptions = navOptions
    )
}

fun NavController.navigateCompleteDetail(
    id: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CompleteDetail(id = id),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.groupDetailNavGraph(
    navHostController: NavController
) {
    composable<GroupDetail> {
        val args = it.toRoute<GroupDetail>() // 이전 화면에서 데이터 전달 받기
        GroupDetailRoute(
            id = args.id,
            navigateUp = navHostController::navigateUp,
            navigateToCompleteDetail = { id ->
                navHostController.navigateCompleteDetail(id = id)
            }
        )
    }

    composable<CompleteDetail> {
        val args = it.toRoute<CompleteDetail>() // 이전 화면에서 데이터 전달 받기
        CompleteDetailRoute(
            id = args.id,
            navigateUp = navHostController::navigateUp
        )
    }
}

@Serializable
data class GroupDetail(
    val id: Long
) : Route

@Serializable
data class CompleteDetail(
    val id: Long
) : Route