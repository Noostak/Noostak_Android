package com.sopt.presentation.mypage

import com.sopt.core.type.DialogType

sealed interface MyPageSideEffect {
    data class ShowDialog(val dialogType: DialogType) : MyPageSideEffect
}
