package com.sopt.presentation.calendar

import androidx.lifecycle.ViewModel
import com.sopt.domain.entity.CalendarEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarEntity())
    val uiState: StateFlow<CalendarEntity> = _uiState

    fun updateTime(newTime: Int) {
        _uiState.value = _uiState.value.copy(time = newTime)
    }
    fun updateAppointName(name: String) {
        _uiState.value = _uiState.value.copy(appointName = name)
    }

    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }
}
