package com.sopt.presentation.appointmentCreate.appointmentSubmit

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentSubmitViewModel @Inject constructor() :
    BaseViewModel<AppointmentSubmitSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentSubmitSideEffect.NavigateUp)
    }

    fun navigateToAppointmentSubmitConfirm(
        groupId: Long,
        appointmentName: String,
        appointmentDate: String,
        appointmentTime: String?,
        appointmentCategory: String,
        appointmentDuration: Int
    ) {
        emitSideEffect(
            AppointmentSubmitSideEffect.NavigateToAppointmentSubmitConfirm(
                groupId = groupId,
                appointmentName = appointmentName,
                appointmentDate = appointmentDate,
                appointmentTime = appointmentTime,
                appointmentCategory = appointmentCategory,
                appointmentDuration = appointmentDuration
            )
        )
    }
}

sealed class AppointmentSubmitSideEffect {
    data object NavigateUp : AppointmentSubmitSideEffect()
    data class NavigateToAppointmentSubmitConfirm(
        val groupId: Long,
        val appointmentName: String,
        val appointmentDate: String,
        val appointmentTime: String?,
        val appointmentCategory: String,
        val appointmentDuration: Int
    ) : AppointmentSubmitSideEffect()
}
