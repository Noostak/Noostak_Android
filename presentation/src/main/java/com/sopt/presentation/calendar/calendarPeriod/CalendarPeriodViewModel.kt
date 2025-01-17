package com.sopt.presentation.calendar.calendarPeriod

import android.content.Context
import com.sopt.core.extension.stringOf
import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class CalendarPeriodViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<CalendarPeriodSideEffect>() {
    val days = immutableListOf(
        context.stringOf(R.string.text_calendar_appointment_mon),
        context.stringOf(R.string.text_calendar_appointment_tues),
        context.stringOf(R.string.text_calendar_appointment_wed),
        context.stringOf(R.string.text_calendar_appointment_thurs),
        context.stringOf(R.string.text_calendar_appointment_fri),
        context.stringOf(R.string.text_calendar_appointment_sat),
        context.stringOf(R.string.text_calendar_appointment_sun)
    )

    fun navigateToCalendarTimePicker(
        appointmentName: String,
        category: String,
        time: Int,
        isSingleDateMode: Boolean,
        dates: List<String>
    ) {
        emitSideEffect(
            CalendarPeriodSideEffect.NavigateToTimePicker(
                appointmentName,
                category,
                time,
                isSingleDateMode,
                dates
            )
        )
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
