package com.sopt.presentation.calendar.calendarTimePicker

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarTimePickerViewModel @Inject constructor() : BaseViewModel<CalendarTimePickerSideEffect>() {

    fun navigateToCalendarCheck(appointmentName: String, category: String, time: Int, isSingleDateMode: Boolean, dates: List<String>, selectTime: String) {
        emitSideEffect(CalendarTimePickerSideEffect.NavigateToCheck(appointmentName, category, time, isSingleDateMode, dates, selectTime))
    }
}

sealed class CalendarTimePickerSideEffect {
    data class NavigateToCheck(
        val appointmentName: String,
        val category: String,
        val time: Int,
        val isSingleDateMode: Boolean,
        val dates: List<String>,
        val selectTime: String
    ) : CalendarTimePickerSideEffect()
}
