package com.sopt.presentation.appointmentCreate.appointmentCreateInfo

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class AppointmentCreateInfoViewModel @Inject constructor() : BaseViewModel<AppointmentCreateInfoSideEffect>() {
    val categories = immutableListOf("중요", "일정", "취미", "기타")

    fun navigateToAppointmentCreatePeriod(groupId: Long, appointmentName: String, appointmentCategory: String, appointmentDuration: Int) {
        emitSideEffect(
            AppointmentCreateInfoSideEffect.NavigateToPeriod(
                groupId = groupId,
                appointmentName,
                appointmentCategory,
                appointmentDuration
            )
        )
    }

    fun navigateUp() {
        emitSideEffect(AppointmentCreateInfoSideEffect.NavigateUp)
    }
}

sealed class AppointmentCreateInfoSideEffect {
    data class NavigateToPeriod(
        val groupId: Long,
        val appointmentName: String,
        val appointmentCategory: String,
        val appointmentDuration: Int
    ) : AppointmentCreateInfoSideEffect()

    data object NavigateUp : AppointmentCreateInfoSideEffect()
}
