package com.sopt.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    nickname: String,
    profileImage: String? = null,
    navOptions: NavOptions? = null
) {
    navigate(
        route = EditProfile(
            nickname = nickname,
            profileImage = profileImage.toString()
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myPageNavGraph(
    navHostController: NavHostController
) {
    composable<MyPage> {
        MyPageRoute(
            navigateToEditProfile = { nickname, profileImage ->
                navHostController.navigateEditProfile(
                    nickname = nickname,
                    profileImage = profileImage
                )
            }
        )
    }

    composable<EditProfile> {
        val args = it.toRoute<EditProfile>()
        EditProfileRoute(
            nickname = args.nickname,
            profileImage = args.profileImage,
            navigateUp = navHostController::navigateUp,
            navigateToMyPage = { navHostController.navigate(MyPage) }
        )
    }
}

@Serializable
data object MyPage : MainTabRoute

@Serializable
data class EditProfile(
    val nickname: String,
    val profileImage: String
) : Route
