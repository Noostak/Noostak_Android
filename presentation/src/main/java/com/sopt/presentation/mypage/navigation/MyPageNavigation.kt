package com.sopt.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.mypage.MyPageRoute
import com.sopt.presentation.mypage.editProfile.EditProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMyPage(navOptions: NavOptions? = null) {
    navigate(
        route = MyPage,
        navOptions = navOptions
    )
}

fun NavController.navigateEditProfile(
    navOptions: NavOptions? = null
) {
    navigate(
        route = EditProfile,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myPageNavGraph(
    navHostController: NavHostController
) {
    composable<MyPage> {
        MyPageRoute()
    }

    composable<EditProfile> {
        EditProfileRoute(
            navigateUp = navHostController::navigateUp,
            navigateToMyPage = {
                navHostController.navigateMyPage()
            }
        )
    }
}

@Serializable
data object MyPage : MainTabRoute

@Serializable
data object EditProfile : Route
