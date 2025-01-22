package com.sopt.presentation.appointmentCreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.appointmentCreate.appointmentSubmit.AppointmentSubmitRoute
import com.sopt.presentation.appointmentCreate.appointmentSubmitComplete.AppointmentSubmitCompleteRoute
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateAppointmentSubmit(
    groupId: Long,
    appointmentName: String,
    isConsecutive: Boolean,
    appointmentDate: List<String>,
    appointmentTime: String?,
    appointmentCategory: String,
    appointmentDuration: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentSubmit(
            groupId = groupId,
            appointmentName = appointmentName,
            isConsecutive = isConsecutive,
            appointmentDate = appointmentDate,
            appointmentTime = appointmentTime,
            appointmentCategory = appointmentCategory,
            appointmentDuration = appointmentDuration
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateAppointmentSubmitComplete(
    groupId: Long,
    appointmentName: String,
    isConsecutive: Boolean,
    appointmentDate: List<String>,
    appointmentTime: String?,
    appointmentCategory: String,
    appointmentDuration: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentSubmitComplete(
            groupId = groupId,
            appointmentName = appointmentName,
            isConsecutive = isConsecutive,
            appointmentDate = appointmentDate,
            appointmentTime = appointmentTime,
            appointmentCategory = appointmentCategory,
            appointmentDuration = appointmentDuration
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.appointmentCreateNavGraph(
    navHostController: NavController
) {
    composable<AppointmentSubmit> {
        val args = it.toRoute<AppointmentSubmit>() // 이전 화면에서 데이터 전달 받기
        AppointmentSubmitRoute(
            groupId = args.groupId,
            appointmentName = args.appointmentName,
            isConsecutive = args.isConsecutive,
            appointmentDate = args.appointmentDate,
            appointmentTime = args.appointmentTime,
            appointmentCategory = args.appointmentCategory,
            appointmentDuration = args.appointmentDuration,
            navigateUp = navHostController::navigateUp,
            navigateToAppointmentSubmitConfirm = { groupId, appointmentName, isConsecutive, appointmentDate, appointmentTime, appointmentCategory, appointmentDuration ->
                navHostController.navigateAppointmentSubmitComplete(
                    groupId = groupId,
                    appointmentName = appointmentName,
                    isConsecutive = isConsecutive,
                    appointmentDate = appointmentDate,
                    appointmentTime = appointmentTime,
                    appointmentCategory = appointmentCategory,
                    appointmentDuration = appointmentDuration
                )
            }
        )
    }

    composable<AppointmentSubmitComplete> {
        val args = it.toRoute<AppointmentSubmitComplete>()
        AppointmentSubmitCompleteRoute(
            groupId = args.groupId,
            appointmentName = args.appointmentName,
            isConsecutive = args.isConsecutive,
            appointmentDate = args.appointmentDate,
            appointmentTime = args.appointmentTime,
            appointmentCategory = args.appointmentCategory,
            appointmentDuration = args.appointmentDuration,
            navigateToGroupDetail = { groupId ->
                navHostController.navigateGroupDetail(groupId = groupId)
            }
        )
    }
}

@Serializable
data class AppointmentSubmit(
    val groupId: Long,
    val appointmentName: String,
    val isConsecutive: Boolean,
    val appointmentDate: List<String>,
    val appointmentTime: String?,
    val appointmentCategory: String,
    val appointmentDuration: Int
) : Route

@Serializable
data class AppointmentSubmitComplete(
    val groupId: Long,
    val appointmentName: String,
    val isConsecutive: Boolean,
    val appointmentDate: List<String>,
    val appointmentTime: String?,
    val appointmentCategory: String,
    val appointmentDuration: Int
) : Route
