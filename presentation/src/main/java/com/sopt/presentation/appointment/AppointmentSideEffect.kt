package com.sopt.presentation.appointment

import com.sopt.domain.entity.TimeEntity

sealed class AppointmentSideEffect {
    data object NavigateUp : AppointmentSideEffect()
    data class NavigateToAppointmentCheck(
        val groupId: Long,
        val appointmentsId: Long,
        val appointmentName: String,
        val availablePeriods: List<TimeEntity>
    ) : AppointmentSideEffect()

    data class NavigateToAppointmentConfirm(
        val groupId: Long,
        val appointmentsId: Long,
        val optionId: Long,
        val appointmentName: String
    ) : AppointmentSideEffect()

    data object ShowDialog : AppointmentSideEffect()
}
