package com.sopt.presentation.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.appointment.AppointmentRoute
import com.sopt.presentation.appointment.appointmentCheck.AppointmentCheckRoute
import com.sopt.presentation.appointment.appointmentConfirm.AppointmentConfirmRoute
import com.sopt.presentation.groupDetail.navigation.GroupDetail
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateAppointment(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Appointment(
            groupId = groupId,
            appointmentsId = appointmentsId,
            appointmentName = appointmentName
        ),
        navOptions = navOptions ?: NavOptions.Builder()
            .setPopUpTo(
                route = GroupDetail(groupId = groupId),
                inclusive = false
            )
            .build()
    )
}

fun NavController.navigateAppointmentCheck(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentCheck(
            groupId = groupId,
            appointmentsId = appointmentsId,
            appointmentName = appointmentName
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateAppointmentConfirm(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    optionId: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentConfirm(
            groupId = groupId,
            appointmentsId = appointmentsId,
            appointmentName = appointmentName,
            optionId = optionId
        ),
        navOptions = navOptions
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
            navigateUp = navHostController::navigateUp,
            navigateToAppointmentCheck = { groupId, appointmentsId, appointmentName ->
                navHostController.navigateAppointmentCheck(
                    groupId = groupId,
                    appointmentsId = appointmentsId,
                    appointmentName = appointmentName
                )
            },
            navigateToAppointmentConfirm = { groupId, appointmentsId, optionId, appointmentName ->
                navHostController.navigateAppointmentConfirm(
                    groupId = groupId,
                    appointmentsId = appointmentsId,
                    optionId = optionId,
                    appointmentName = appointmentName
                )
            }
        )
    }

    composable<AppointmentCheck> {
        val args = it.toRoute<AppointmentCheck>()
        AppointmentCheckRoute(
            groupId = args.groupId,
            appointmentsId = args.appointmentsId,
            appointmentName = args.appointmentName,
            navigateUp = navHostController::navigateUp,
            navigateToAppointment = { groupId, appointmentsId, appointmentName ->
                navHostController.navigateAppointment(
                    groupId = groupId,
                    appointmentsId = appointmentsId,
                    appointmentName = appointmentName
                )
            },
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            }
        )
    }

    composable<AppointmentConfirm> {
        val args = it.toRoute<AppointmentConfirm>()
        AppointmentConfirmRoute(
            groupId = args.groupId,
            appointmentsId = args.appointmentsId,
            optionId = args.optionId,
            appointmentName = args.appointmentName,
            navigateUp = navHostController::navigateUp,
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            }
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

@Serializable
data class AppointmentConfirm(
    val groupId: Long,
    val appointmentsId: Long,
    val optionId: Long,
    val appointmentName: String
) : Route
