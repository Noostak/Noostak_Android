package com.sopt.presentation.appointment.appointmentCheck

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.TimeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentCheckViewModel @Inject constructor() :
    BaseViewModel<AppointmentCheckSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentCheckSideEffect.NavigateUp)
    }

    fun navigateToAppointment(groupId: Long, appointmentId: Long, appointmentName: String) {
        emitSideEffect(
            AppointmentCheckSideEffect.NavigateToAppointment(
                groupId,
                appointmentId,
                appointmentName
            )
        )
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentCheckSideEffect.NavigateToGroupDetail(groupId))
    }

    val mockAvailablePeriods = listOf(
        TimeEntity(
            date = "2024-09-05T10:00:00",
            startTime = "2024-09-05T10:00:00",
            endTime = "2024-09-05T18:00:00"
        ),
        TimeEntity(
            date = "2024-09-06T10:00:00",
            startTime = "2024-09-06T10:00:00",
            endTime = "2024-09-06T18:00:00"
        ),
        TimeEntity(
            date = "2024-09-07T10:00:00",
            startTime = "2024-09-07T10:00:00",
            endTime = "2024-09-07T18:00:00"
        )
    )
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
    data class NavigateToAppointment(
        val groupId: Long,
        val appointmentId: Long,
        val appointmentName: String
    ) : AppointmentCheckSideEffect()

    data class NavigateToGroupDetail(val groupId: Long) : AppointmentCheckSideEffect()
}
