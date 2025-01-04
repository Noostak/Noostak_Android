package com.sopt.presentation.appointment.appointmentCheck

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentCheckViewModel @Inject constructor() :
    BaseViewModel<AppointmentCheckSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentCheckSideEffect.NavigateUp)
    }

    fun navigateToAppointment(groupId: Long, appointmentsId: Long, appointmentName: String) {
        emitSideEffect(
            AppointmentCheckSideEffect.NavigateToAppointment(
                groupId,
                appointmentsId,
                appointmentName
            )
        )
    }
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
    data class NavigateToAppointment(
        val groupId: Long,
        val appointmentsId: Long,
        val appointmentName: String
    ) : AppointmentCheckSideEffect()
}
