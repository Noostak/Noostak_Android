package com.sopt.presentation.appointment.appointmentCheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentCheckViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects: MutableSharedFlow<AppointmentCheckSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<AppointmentCheckSideEffect> get() = _sideEffects.asSharedFlow()

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(AppointmentCheckSideEffect.NavigateUp)
        }
    }
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
}
