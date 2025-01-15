package com.sopt.presentation.calendar.calendarPeriod

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class CalendarPeriodViewModel @Inject constructor() : BaseViewModel<CalendarPeriodSideEffect>() {
    val days = immutableListOf("일", "월", "화", "수", "목", "금", "토")

    fun navigateToCalendarTimePicker(appointmentName: String, category: String, time: Int, isSingleDateMode: Boolean, dates: List<String>) {
        emitSideEffect(CalendarPeriodSideEffect.NavigateToTimePicker(appointmentName, category, time, isSingleDateMode, dates))
    }
}

sealed class CalendarPeriodSideEffect {
    data class NavigateToTimePicker(
        val appointmentName: String,
        val category: String,
        val time: Int,
        val isSingleDateMode: Boolean,
        val dates: List<String>
    ) : CalendarPeriodSideEffect()
}
