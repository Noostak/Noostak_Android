package com.sopt.presentation.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.presentation.calendar.CalendarRoute
import com.sopt.presentation.calendar.screen.DetailScreen
import com.sopt.presentation.calendar.screen.InfoScreen
import kotlinx.serialization.Serializable

fun NavController.navigateCalendar(navOptions: NavOptions? = null) {
    navigate(
        route = Calendar,
        navOptions = navOptions
    )
}

fun NavController.navigateInfoScreen(navOptions: NavOptions? = null) {
    navigate(
        route = Info,
        navOptions = navOptions
    )
}

fun NavController.navigateDetailScreen(navOptions: NavOptions? = null) {
    navigate(
        route = Detail,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.calendarNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavController
) {
    composable<Calendar> {
        CalendarRoute(
            paddingValues = paddingValues,
            navigateToInfoScreen = {
                navHostController.navigateInfoScreen()
            }
        )
    }

    composable<Info> {
        InfoScreen(
            viewModel = hiltViewModel(),
            onNextClick = {
                navHostController.navigateDetailScreen()
            }
        )
    }

    composable<Detail> {
        DetailScreen(
            onBackClick = { navHostController.navigateUp() },
            onNextClick = {

            })
    }
}

@Serializable
data object Calendar : MainTabRoute

@Serializable
data object Info : MainTabRoute

@Serializable
data object Detail : MainTabRoute
