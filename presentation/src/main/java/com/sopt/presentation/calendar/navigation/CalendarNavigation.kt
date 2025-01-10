package com.sopt.presentation.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.MainTabRoute
import com.sopt.presentation.calendar.CalendarRoute
import com.sopt.presentation.calendar.calendarInfo.CalendarInfoRoute
import com.sopt.presentation.calendar.calendarPeriod.CalendarPeriodRoute
import kotlinx.serialization.Serializable

fun NavController.navigateCalendar(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Calendar,
        navOptions = navOptions
    )
}

fun NavController.navigateCalendarInfo(
    navOptions: NavOptions? = null
) {
    navigate(
        route = CalendarInfo,
        navOptions = navOptions
    )
}

fun NavController.navigateCalendarPeriod(
    appointmentName: String,
    category: String,
    time: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CalendarPeriod(appointmentName = appointmentName, category = category, time = time),
        navOptions = navOptions
    )
}

fun NavController.navigateCalendarTimePicker(
    appointmentName: String,
    category: String,
    time: Int,
    startDate: String,
    endDate: String,
    dates: List<String>,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CalendarTimePicker(appointmentName = appointmentName, category = category, time = time, startDate = startDate, endDate = endDate, dates = dates),
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
                navHostController.navigateCalendarInfo()
            }
        )
    }

    composable<CalendarInfo> {
        CalendarInfoRoute(
            navigateToPeriod = { appointmentName, category, time ->
                navHostController.navigateCalendarPeriod(
                    appointmentName = appointmentName,
                    category = category,
                    time = time
                )
            }
        )
    }

    composable<CalendarPeriod> {
        val args = it.toRoute<CalendarPeriod>()
        CalendarPeriodRoute(
            appointmentName = args.appointmentName,
            category = args.category,
            time = args.time,
            navigateToTimePicker = { appointmentName, category, time , startDate, endDate, dates ->
                navHostController.navigateCalendarTimePicker(
                    appointmentName = appointmentName,
                    category = category,
                    time = time,
                    startDate = startDate,
                    endDate = endDate,
                    dates = dates
                )
            }
        )
    }
}

@Serializable
object Calendar : MainTabRoute

@Serializable
object CalendarInfo : MainTabRoute

@Serializable
data class CalendarPeriod(
    val appointmentName: String,
    val category: String,
    val time: Int
) : MainTabRoute

@Serializable
data class CalendarTimePicker(
    val appointmentName: String,
    val category: String,
    val time: Int,
    val startDate: String = "",
    val endDate: String = "",
    val dates: List<String>? = null
) : MainTabRoute