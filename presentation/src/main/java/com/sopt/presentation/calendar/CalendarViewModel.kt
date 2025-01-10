package com.sopt.presentation.calendar.viewmodel

import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.calendar.CalendarSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(): BaseViewModel<CalendarSideEffect>() {

    fun navigateToCalendarInfoScreen() {
        emitSideEffect(CalendarSideEffect.NavigateToInfo)
    }
}
