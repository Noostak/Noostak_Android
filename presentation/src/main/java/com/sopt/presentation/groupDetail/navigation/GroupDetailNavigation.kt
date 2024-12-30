package com.sopt.presentation.groupDetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.groupDetail.GroupDetailRoute
import com.sopt.presentation.groupDetail.screen.ConfirmedDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateGroupDetail(
    groupId: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = GroupDetail(groupId = groupId),
        navOptions = navOptions
    )
}

fun NavController.navigateConfirmedDetail(
    groupId: Long,
    confirmedId: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ConfirmedDetail(
            groupId = groupId,
            confirmedId = confirmedId
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.groupDetailNavGraph(
    navHostController: NavController
) {
    composable<GroupDetail> {
        val args = it.toRoute<GroupDetail>() // 이전 화면에서 데이터 전달 받기
        GroupDetailRoute(
            groupId = args.groupId,
            navigateUp = navHostController::navigateUp,
            navigateToConfirmedDetail = { groupId, confirmedId ->
                navHostController.navigateConfirmedDetail(
                    groupId = groupId,
                    confirmedId = confirmedId
                )
            }
        )
    }

    composable<ConfirmedDetail> {
        val args = it.toRoute<ConfirmedDetail>() // 이전 화면에서 데이터 전달 받기
        ConfirmedDetailRoute(
            groupId = args.groupId,
            confirmedId = args.confirmedId,
            navigateUp = navHostController::navigateUp
        )
    }
}

@Serializable
data class GroupDetail(
    val groupId: Long
) : Route

@Serializable
data class ConfirmedDetail(
    val groupId: Long,
    val confirmedId: Long
) : Route