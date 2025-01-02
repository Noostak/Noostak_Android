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
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
}
