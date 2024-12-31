package com.sopt.presentation.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.appointment.appointmentCheck.AppointmentCheckRoute
import com.sopt.presentation.appointment.AppointmentRoute
import kotlinx.serialization.Serializable

fun NavController.navigateAppointment(
    groupId: Long,
    appointmentsId: Long
) {
    navigate(
        route = Appointment(
            groupId = groupId,
            appointmentsId = appointmentsId
        )
    )
}

fun NavController.navigateAppointmentCheck(
    groupId: Long,
    appointmentsId: Long
) {
    navigate(
        route = AppointmentCheck(
            groupId = groupId,
            appointmentsId = appointmentsId
        )
    )
}

fun NavGraphBuilder.appointmentNavGraph(
    navHostController: NavHostController
) {
    composable<Appointment> {
        val args = it.toRoute<Appointment>()
        AppointmentRoute(
            groupId = args.groupId,
            appointmentsId = args.appointmentsId,
            navigateUp = navHostController::navigateUp
        )
    }

    composable<AppointmentCheck> {
        val args = it.toRoute<AppointmentCheck>()
        AppointmentCheckRoute(
            groupId = args.groupId,
            appointmentsId = args.appointmentsId,
            navigateUp = navHostController::navigateUp
        )
    }
}

@Serializable
data class Appointment(
    val groupId: Long,
    val appointmentsId: Long
) : Route

@Serializable
data class AppointmentCheck(
    val groupId: Long,
    val appointmentsId: Long
) : Route
