package com.sopt.presentation.groupCreate.groupCreateSuccess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.groupCreate.groupCreateSuccess.GroupCreateSuccessRoute
import com.sopt.presentation.groupCreate.navigation.GroupCreate
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateToGroupCreateSuccess(
    groupId: Long,
    groupInviteCode: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = GroupCreateSuccess(groupId = groupId, groupInviteCode = groupInviteCode),
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(GroupCreate, inclusive = true)
            .build()
    )
}

fun NavGraphBuilder.groupCreateSuccessNavGraph(
    navHostController: NavController
) {
    composable<GroupCreateSuccess> {
        val args = it.toRoute<GroupCreateSuccess>()
        GroupCreateSuccessRoute(
            groupId = args.groupId,
            groupInviteCode = args.groupInviteCode,
            navigateToGroupDetail = { groupId ->
                navHostController.popBackStack()
                navHostController.navigateGroupDetail(groupId = groupId)
            }
        )
    }
}

@Serializable
data class GroupCreateSuccess(val groupId: Long, val groupInviteCode: String) : Route
