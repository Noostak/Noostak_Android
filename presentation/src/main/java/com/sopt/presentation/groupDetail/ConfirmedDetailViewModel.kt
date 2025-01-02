package com.sopt.presentation.groupDetail

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ConfirmedDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmedDetailViewModel @Inject constructor() : BaseViewModel<ConfirmedDetailSideEffect>() {
    fun navigateUp() {
        emitSideEffect(ConfirmedDetailSideEffect.NavigateUp)
    }

    val mockConfirmedDetail = ConfirmedDetailEntity(
        appointName = "3차 회의",
        date = "2024-09-27",
        weekday = "금요일",
        startTime = "11:00",
        endTime = "14:00",
        category = "기타",
        likes = 15,
        availableMembersCount = 5,
        availableMembers = listOf(
            "나", "선우정아", "대한민국만세", "최영희", "정영수",
            "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
    )
}

sealed class ConfirmedDetailSideEffect {
    data object NavigateUp : ConfirmedDetailSideEffect()
}
