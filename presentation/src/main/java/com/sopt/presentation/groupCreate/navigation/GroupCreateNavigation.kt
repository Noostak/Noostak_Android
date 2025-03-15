package com.sopt.presentation.groupCreate.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.groupCreateNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavController
) {
    composable<GroupCreate> {
        GroupCreateRoute(
            paddingValues = paddingValues,
            navigateToGroupCreateSuccess = { groupInviteCode ->
                navHostController.navigateToGroupCreateSuccess(groupInviteCode = groupInviteCode)
            }
        )
    }
}

@Serializable
data object GroupCreate : Route
