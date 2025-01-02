package com.sopt.presentation.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.appointment.AppointmentRoute
import com.sopt.presentation.appointment.appointmentCheck.AppointmentCheckRoute
import kotlinx.serialization.Serializable

fun NavController.navigateAppointment(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String
) {
    navigate(
        route = Appointment(
            groupId = groupId,
            appointmentsId = appointmentsId,
            appointmentName = appointmentName
        )
    )
}

fun NavController.navigateAppointmentCheck(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String
) {
    navigate(
        route = AppointmentCheck(
            groupId = groupId,
            appointmentsId = appointmentsId,
            appointmentName = appointmentName
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
            appointmentName = args.appointmentName,
            navigateUp = navHostController::navigateUp
        )
    }

    composable<AppointmentCheck> {
        val args = it.toRoute<AppointmentCheck>()
        AppointmentCheckRoute(
            groupId = args.groupId,
            appointmentsId = args.appointmentsId,
            appointmentName = args.appointmentName,
            navigateUp = navHostController::navigateUp
        )
    }
}

@Serializable
data class Appointment(
    val groupId: Long,
    val appointmentsId: Long,
    val appointmentName: String
) : Route

@Serializable
data class AppointmentCheck(
    val groupId: Long,
    val appointmentsId: Long,
    val appointmentName: String
) : Route
