package com.sopt.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.R
import com.sopt.presentation.calendar.navigation.Calendar
import com.sopt.presentation.group.navigation.Group
import com.sopt.presentation.mypage.navigation.MyPage

enum class MainTab(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val contentDescription: Int,
    val route: MainTabRoute
) {
    CALENDAR(
        selectedIcon = R.drawable.ic_bnv_calendar_on,
        unselectedIcon = R.drawable.ic_bnv_calendar_off,
        contentDescription = R.string.bottom_nav_calendar,
        route = Calendar
    ),
    GROUP(
        selectedIcon = R.drawable.ic_bnv_group_on,
        unselectedIcon = R.drawable.ic_bnv_group_off,
        contentDescription = R.string.bottom_nav_group,
        route = Group
    ),
    MY_PAGE(
        selectedIcon = R.drawable.ic_bnv_mypage_on,
        unselectedIcon = R.drawable.ic_bnv_mypage_off,
        contentDescription = R.string.bottom_nav_my_page,
        route = MyPage
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
