package com.sopt.presentation.appointmentCreate.appointmentCreateTimePicker

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentCreateTimePickerViewModel @Inject constructor() :
    BaseViewModel<AppointmentCreateTimePickerSideEffect>() {

    fun navigateToCalendarCheck(
        groupId: Long,
        appointmentName: String,
        appointmentCategory: String,
        appointmentDuration: Int,
        isSingleDateMode: Boolean,
        appointmentDate: List<String>,
        appointmentTime: String
    ) {
        emitSideEffect(
            AppointmentCreateTimePickerSideEffect.NavigateToCheck(
                groupId = groupId,
                appointmentName,
                appointmentCategory,
                appointmentDuration,
                isSingleDateMode,
                appointmentDate,
                appointmentTime
            )
        )
    }

    fun navigateUp() {
        emitSideEffect(AppointmentCreateTimePickerSideEffect.NavigateUp)
    }
}

sealed class AppointmentCreateTimePickerSideEffect {
    data class NavigateToCheck(
        val groupId: Long,
        val appointmentName: String,
        val appointmentCategory: String,
        val appointmentDuration: Int,
        val isSingleDateMode: Boolean,
        val appointmentDate: List<String>,
        val appointmentTime: String
    ) : AppointmentCreateTimePickerSideEffect()
    data object NavigateUp : AppointmentCreateTimePickerSideEffect()
}
