package com.sopt.presentation.groupDetail.confirmedDetail

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ConfirmedDetailEntity
import com.sopt.domain.entity.IdentityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmedDetailViewModel @Inject constructor() : BaseViewModel<ConfirmedDetailSideEffect>() {
    fun navigateUp() {
        emitSideEffect(ConfirmedDetailSideEffect.NavigateUp)
    }

    val mockConfirmedDetail = ConfirmedDetailEntity(
        myIdentity = IdentityEntity(
            availability = "available",
            position = 0,
            name = "이가을"
        ),
        date = "2025-01-06T00:00:00",
        startTime = "2025-01-06T11:00:00",
        endTime = "2025-01-06T14:00:00",
        category = "기타",
        likes = 15,
        liked = true,
        availableMembersCount = 5,
        availableMembers = listOf(
            "이가을", "대한민국만세", "최영희", "정영수",
            "선우정아", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf(
            "류선재", "한강", "이영희", "박영수", "최영희", "정영수", "김태성", "백인혁", "임솔"
        )
    )
}

sealed class ConfirmedDetailSideEffect {
    data object NavigateUp : ConfirmedDetailSideEffect()
}
