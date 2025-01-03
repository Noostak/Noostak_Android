package com.sopt.presentation.group.groupCreateSuccess

sealed interface GroupCreateSuccessSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : GroupCreateSuccessSideEffect
    data class ShowSnackBar(val message: Int) : GroupCreateSuccessSideEffect
}