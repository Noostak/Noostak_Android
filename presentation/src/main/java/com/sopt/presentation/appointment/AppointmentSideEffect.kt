package com.sopt.presentation.appointment

sealed class AppointmentSideEffect {
    data object NavigateUp : AppointmentSideEffect()
    data class NavigateToAppointmentCheck(
        val groupId: Long,
        val appointmentsId: Long,
        val appointmentName: String
    ) : AppointmentSideEffect()

    data class NavigateToAppointmentConfirm(
        val groupId: Long,
        val appointmentsId: Long,
        val optionId: Long,
        val appointmentName: String
    ) : AppointmentSideEffect()

    data object ShowDialog : AppointmentSideEffect()
}
