package com.sopt.presentation.calendar.calendarInfo

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class CalendarInfoViewModel @Inject constructor() : BaseViewModel<CalendarInfoSideEffect>() {
    val categories = immutableListOf("중요", "일정", "취미", "기타")

    fun navigateToCalendarPeriod(appointmentName: String, category: String, time: Int) {
        emitSideEffect(CalendarInfoSideEffect.NavigateToPeriod(appointmentName, category, time))
    }
}

sealed class CalendarInfoSideEffect {
    data class NavigateToPeriod(
        val appointmentName: String,
        val category: String,
        val time: Int
    ) : CalendarInfoSideEffect()
}
