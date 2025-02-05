package com.sopt.presentation.groupCreate

sealed interface GroupCreateSideEffect {
    data object RequestImagePicker : GroupCreateSideEffect
    data object ShowSnackBar : GroupCreateSideEffect
    data object ShowDialog : GroupCreateSideEffect
    data object NavigateToGroupCreateSuccess : GroupCreateSideEffect
}
