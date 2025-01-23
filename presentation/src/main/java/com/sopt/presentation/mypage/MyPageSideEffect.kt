package com.sopt.presentation.mypage

import com.sopt.core.type.DialogType

sealed interface MyPageSideEffect {
    data class NavigateToEditProfile(
        val nickname: String,
        val profileImage: String?
    ) : MyPageSideEffect

    data class ShowDialog(val dialogType: DialogType) : MyPageSideEffect
}
