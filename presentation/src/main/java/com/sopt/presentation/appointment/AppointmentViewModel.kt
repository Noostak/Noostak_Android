package com.sopt.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects: MutableSharedFlow<AppointmentSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<AppointmentSideEffect> get() = _sideEffects.asSharedFlow()

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(AppointmentSideEffect.NavigateUp)
        }
    }
}

sealed class AppointmentSideEffect {
    data object NavigateUp : AppointmentSideEffect()
}
