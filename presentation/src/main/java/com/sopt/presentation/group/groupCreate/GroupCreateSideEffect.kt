package com.sopt.presentation.group.groupCreate

sealed interface GroupCreateSideEffect {
    data object RequestImagePicker : GroupCreateSideEffect

    data object ShowPermissionDeniedDialog : GroupCreateSideEffect

    data object NavigateToGroupCreateSuccess : GroupCreateSideEffect
}