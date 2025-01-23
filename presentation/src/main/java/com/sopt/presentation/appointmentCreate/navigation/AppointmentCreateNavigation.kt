package com.sopt.presentation.appointmentCreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.appointmentCreate.appointmentCreateInfo.AppointmentCreateInfoRoute
import com.sopt.presentation.appointmentCreate.appointmentCreatePeriod.AppointmentCreatePeriodRoute
import com.sopt.presentation.appointmentCreate.appointmentCreateTimePicker.AppointmentCreateTimePickerRoute
import com.sopt.presentation.appointmentCreate.appointmentSubmit.AppointmentSubmitRoute
import com.sopt.presentation.appointmentCreate.appointmentSubmitComplete.AppointmentSubmitCompleteRoute
import com.sopt.presentation.groupDetail.navigation.navigateGroupDetail
import kotlinx.serialization.Serializable

fun NavController.navigateAppointmentCreateInfo(
    groupId: Long,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentCreateInfo(groupId = groupId),
        navOptions = navOptions
    )
}

fun NavController.navigateAppointmentCreatePeriod(
    groupId: Long,
    appointmentName: String,
    appointmentCategory: String,
    appointmentTime: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentCreatePeriod(
            groupId = groupId,
            appointmentName = appointmentName,
            appointmentCategory = appointmentCategory,
            appointmentTime = appointmentTime
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateAppointmentCreateTimePicker(
    groupId: Long,
    appointmentName: String,
    appointmentCategory: String,
    appointmentTime: Int,
    isSingleDateMode: Boolean,
    appointmentDate: List<String>?,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AppointmentCreateTimePicker(
            groupId = groupId,
            appointmentName = appointmentName,
            appointmentCategory = appointmentCategory,
            appointmentTime = appointmentTime,
            isSingleDateMode = isSingleDateMode,
            appointmentDate = appointmentDate
        ),
        navOptions = navOptions
    )
}

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
    composable<AppointmentCreateInfo> {
        val args = it.toRoute<AppointmentCreateInfo>()
        AppointmentCreateInfoRoute(
            groupId = args.groupId,
            navigateToPeriod = { groupId, appointmentName, category, time ->
                navHostController.navigateAppointmentCreatePeriod(
                    groupId = groupId,
                    appointmentName = appointmentName,
                    appointmentCategory = category,
                    appointmentTime = time
                )
            },
            navigateUp = navHostController::navigateUp
        )
    }

    composable<AppointmentCreatePeriod> {
        val args = it.toRoute<AppointmentCreatePeriod>()
        AppointmentCreatePeriodRoute(
            groupId = args.groupId,
            appointmentName = args.appointmentName,
            appointmentCategory = args.appointmentCategory,
            appointmentTime = args.appointmentTime,
            navigateToTimePicker = { groupId, appointmentName, category, time, isSingleDateMode, dates ->
                navHostController.navigateAppointmentCreateTimePicker(
                    groupId = groupId,
                    appointmentName = appointmentName,
                    appointmentCategory = category,
                    appointmentTime = time,
                    isSingleDateMode = isSingleDateMode,
                    appointmentDate = dates
                )
            },
            navigateUp = navHostController::navigateUp
        )
    }

    composable<AppointmentCreateTimePicker> {
        val args = it.toRoute<AppointmentCreateTimePicker>()
        AppointmentCreateTimePickerRoute(
            groupId = args.groupId,
            appointmentName = args.appointmentName,
            appointmentCategory = args.appointmentCategory,
            appointmentTime = args.appointmentTime,
            isSingleDateMode = args.isSingleDateMode,
            appointmentDate = args.appointmentDate ?: emptyList(),
            navigateToCheck = { groupId, appointmentName, appointmentCategory, appointmentDuration, isSingleDateMode, appointmentDate, appointmentTime ->
                navHostController.navigateAppointmentSubmit(
                    groupId = groupId,
                    appointmentName = appointmentName,
                    appointmentDate = appointmentDate,
                    appointmentTime = appointmentTime,
                    appointmentCategory = appointmentCategory,
                    appointmentDuration = appointmentDuration,
                    isConsecutive = isSingleDateMode
                )
            },
            navigateUp = navHostController::navigateUp
        )
    }

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
data class AppointmentCreateInfo(
    val groupId: Long
) : Route

@Serializable
data class AppointmentCreatePeriod(
    val groupId: Long,
    val appointmentName: String,
    val appointmentCategory: String,
    val appointmentTime: Int
) : Route

@Serializable
data class AppointmentCreateTimePicker(
    val groupId: Long,
    val appointmentName: String,
    val appointmentCategory: String,
    val appointmentTime: Int,
    val isSingleDateMode: Boolean,
    val appointmentDate: List<String>? = null
) : Route

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
