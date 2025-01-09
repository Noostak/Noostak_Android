package com.sopt.presentation.appointment.appointmentConfirm

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentConfirmViewModel @Inject constructor() :
    BaseViewModel<AppointmentConfirmSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateUp)
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateToGroupDetail(groupId))
    }

    val mockAppointmentDetail = AppointmentDetailEntity(
        appointmentName = "3차 회의",
        date = "9/5",
        startTime = "11:00",
        endTime = "14:00",
        category = "기타",
        availableMembersCount = 22,
        availableMembers = listOf(
            "나", "선우정아", "대한민국만세", "최영희", "정영수",
            "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
    )
}

sealed class AppointmentConfirmSideEffect {
    data object NavigateUp : AppointmentConfirmSideEffect()
    data class NavigateToGroupDetail(
        val groupId: Long
    ) : AppointmentConfirmSideEffect()
}
