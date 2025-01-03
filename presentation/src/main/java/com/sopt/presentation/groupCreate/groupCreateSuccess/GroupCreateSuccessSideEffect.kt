package com.sopt.presentation.groupCreate.groupCreateSuccess

sealed interface GroupCreateSuccessSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : GroupCreateSuccessSideEffect
    data class ShowSnackBar(val message: Int) : GroupCreateSuccessSideEffect
}