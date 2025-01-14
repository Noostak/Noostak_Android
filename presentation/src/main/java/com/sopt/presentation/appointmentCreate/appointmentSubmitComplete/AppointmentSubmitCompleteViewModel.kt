package com.sopt.presentation.appointmentCreate.appointmentSubmitComplete

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentSubmitCompleteViewModel @Inject constructor() :
    BaseViewModel<AppointmentSubmitCompleteSideEffect>() {
    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentSubmitCompleteSideEffect.NavigateToGroupDetail(groupId))
    }
}

sealed class AppointmentSubmitCompleteSideEffect {
    data class NavigateToGroupDetail(
        val groupId: Long
    ) : AppointmentSubmitCompleteSideEffect()
}
