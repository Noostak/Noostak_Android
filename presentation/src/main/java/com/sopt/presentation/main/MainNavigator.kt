package com.sopt.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sopt.presentation.auth.login.navigation.Splash
import com.sopt.presentation.calendar.navigation.navigateCalendar
import com.sopt.presentation.group.navigation.navigateGroup
import com.sopt.presentation.mypage.navigation.navigateMyPage

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Splash

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(0) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
            restoreState = false
        }

        when (tab) {
            MainTab.CALENDAR -> navController.navigateCalendar(navOptions)
            MainTab.GROUP -> navController.navigateGroup(navOptions)
            MainTab.MY_PAGE -> navController.navigateMyPage(navOptions)
        }
    }

    fun getCurrentTab(): MainTab? {
        val currentDestination = navController.currentDestination
        return MainTab.entries.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    @Composable
    fun showBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
