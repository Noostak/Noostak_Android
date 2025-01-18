package com.sopt.presentation.appointmentCreate.appointmentCreatePeriod

import android.content.Context
import com.sopt.core.extension.stringOf
import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class AppointmentCreatePeriodViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<AppointmentCreatePeriodSideEffect>() {
    val days = immutableListOf(
        context.stringOf(R.string.text_calendar_appointment_mon),
        context.stringOf(R.string.text_calendar_appointment_tues),
        context.stringOf(R.string.text_calendar_appointment_wed),
        context.stringOf(R.string.text_calendar_appointment_thurs),
        context.stringOf(R.string.text_calendar_appointment_fri),
        context.stringOf(R.string.text_calendar_appointment_sat),
        context.stringOf(R.string.text_calendar_appointment_sun)
    )

    fun navigateToAppointmentCreateTimePicker(
        groupId: Long,
        appointmentName: String,
        category: String,
        time: Int,
        isSingleDateMode: Boolean,
        dates: List<String>
    ) {
        emitSideEffect(
            AppointmentCreatePeriodSideEffect.NavigateToTimePicker(
                groupId = groupId,
                appointmentName,
                category,
                time,
                isSingleDateMode,
                dates
            )
        )
    }
}

sealed class AppointmentCreatePeriodSideEffect {
    data class NavigateToTimePicker(
        val groupId: Long,
        val appointmentName: String,
        val category: String,
        val time: Int,
        val isSingleDateMode: Boolean,
        val dates: List<String>
    ) : AppointmentCreatePeriodSideEffect()
}
