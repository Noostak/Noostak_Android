package com.sopt.presentation.appointmentCreate.appointmentCreateTimePicker

import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.appointmentCreate.appointmentCreatePeriod.AppointmentCreatePeriodSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentCreateTimePickerViewModel @Inject constructor() :
    BaseViewModel<AppointmentCreateTimePickerSideEffect>() {

    fun navigateToCalendarCheck(
        groupId: Long,
        appointmentName: String,
        category: String,
        time: Int,
        isSingleDateMode: Boolean,
        dates: List<String>,
        selectTime: String
    ) {
        emitSideEffect(
            AppointmentCreateTimePickerSideEffect.NavigateToCheck(
                groupId = groupId,
                appointmentName,
                category,
                time,
                isSingleDateMode,
                dates,
                selectTime
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
        val category: String,
        val time: Int,
        val isSingleDateMode: Boolean,
        val dates: List<String>,
        val selectTime: String
    ) : AppointmentCreateTimePickerSideEffect()
    data object NavigateUp : AppointmentCreateTimePickerSideEffect()
}
