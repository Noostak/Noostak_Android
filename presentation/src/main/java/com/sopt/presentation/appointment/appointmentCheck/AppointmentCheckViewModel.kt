package com.sopt.presentation.appointment.appointmentCheck

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentCheckViewModel @Inject constructor() :
    BaseViewModel<AppointmentCheckSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentCheckSideEffect.NavigateUp)
    }

    fun navigateToAppointment(groupId: Long, appointmentsId: Long, appointmentName: String) {
        emitSideEffect(
            AppointmentCheckSideEffect.NavigateToAppointment(
                groupId,
                appointmentsId,
                appointmentName
            )
        )
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentCheckSideEffect.NavigateToGroupDetail(groupId))
    }

    val mockTimeTableEntity = TimeTableEntity(
        startTime = "07:00",
        endTime = "24:00",
        timeEntity = listOf(
            TimeEntity(
                date = "2024-09-27",
                times = null
            ),
            TimeEntity(
                date = "2024-09-28",
                times = null
            ),
            TimeEntity(
                date = "2024-09-29",
                times = null
            ),
            TimeEntity(
                date = "2024-09-30",
                times = null
            ),
            TimeEntity(
                date = "2024-10-01",
                times = null
            ),
            TimeEntity(
                date = "2024-10-02",
                times = null
            ),
            TimeEntity(
                date = "2024-10-03",
                times = null
            )
        )
    )
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
    data class NavigateToAppointment(
        val groupId: Long,
        val appointmentsId: Long,
        val appointmentName: String
    ) : AppointmentCheckSideEffect()

    data class NavigateToGroupDetail(val groupId: Long) : AppointmentCheckSideEffect()
}
