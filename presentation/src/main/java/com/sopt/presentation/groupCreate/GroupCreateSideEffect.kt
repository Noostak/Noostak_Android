package com.sopt.presentation.groupCreate

sealed interface GroupCreateSideEffect {
    data object RequestImagePicker : GroupCreateSideEffect
    data object ShowSnackBar : GroupCreateSideEffect
    data object ShowErrorDialog : GroupCreateSideEffect
    data class NavigateToGroupCreateSuccess(val groupInviteCode: String) : GroupCreateSideEffect
}
