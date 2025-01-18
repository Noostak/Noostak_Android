package com.sopt.presentation.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.core.navigation.MainTabRoute
import com.sopt.presentation.appointmentCreate.navigation.navigateAppointmentCreateInfo
import com.sopt.presentation.calendar.CalendarRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCalendar(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Calendar,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.calendarNavGraph(
    navHostController: NavController,
    paddingValues: PaddingValues = PaddingValues()
) {
    composable<Calendar> {
        CalendarRoute(
            paddingValues = paddingValues,
            navigateToInfoScreen = {
                navHostController.navigateAppointmentCreateInfo(groupId = 1)
            }
        )
    }
}

@Serializable
object Calendar : MainTabRoute
