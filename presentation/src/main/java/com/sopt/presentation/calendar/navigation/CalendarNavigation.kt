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
import com.sopt.presentation.calendar.calendarTimePicker.CalendarTimePickerRoute
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
    isSingleDateMode: Boolean,
    dates: List<String>?,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CalendarTimePicker(appointmentName = appointmentName, category = category, time = time, isSingleDateMode = isSingleDateMode, dates = dates),
        navOptions = navOptions
    )
}

fun NavController.navigateCalendarCheck(
    appointmentName: String,
    category: String,
    time: Int,
    isSingleDateMode: Boolean,
    dates: List<String>?,
    selectTime: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = CalendarCheck(appointmentName = appointmentName, category = category, time = time, isSingleDateMode = isSingleDateMode, dates = dates, selectTime = selectTime),
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
            navigateToTimePicker = { appointmentName, category, time, isSingleDateMode, dates ->
                navHostController.navigateCalendarTimePicker(
                    appointmentName = appointmentName,
                    category = category,
                    time = time,
                    isSingleDateMode = isSingleDateMode,
                    dates = dates
                )
            }
        )
    }

    composable<CalendarTimePicker> {
        val args = it.toRoute<CalendarTimePicker>()
        CalendarTimePickerRoute(
            appointmentName = args.appointmentName,
            category = args.category,
            time = args.time,
            isSingleDateMode = args.isSingleDateMode,
            dates = args.dates ?: emptyList(),
            navigateToCheck = { appointmentName, category, time, isSingleDateMode, dates, selectTime ->
                navHostController.navigateCalendarCheck(
                    appointmentName = appointmentName,
                    category = category,
                    time = time,
                    isSingleDateMode = isSingleDateMode,
                    dates = dates,
                    selectTime = selectTime
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
    val isSingleDateMode: Boolean,
    val dates: List<String>? = null
) : MainTabRoute

@Serializable
data class CalendarCheck(
    val appointmentName: String,
    val category: String,
    val time: Int,
    val isSingleDateMode: Boolean,
    val dates: List<String>? = null,
    val selectTime: String
) : MainTabRoute
