package com.sopt.presentation.groupCreate

sealed interface GroupCreateSideEffect {
    data object RequestImagePicker : GroupCreateSideEffect
    data object ShowPermissionDeniedDialog : GroupCreateSideEffect
    data object NavigateToGroupCreateSuccess : GroupCreateSideEffect
}
