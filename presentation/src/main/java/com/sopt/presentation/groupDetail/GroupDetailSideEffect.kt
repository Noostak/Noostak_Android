package com.sopt.presentation.groupDetail

sealed class GroupDetailSideEffect {
    data object NavigateUp : GroupDetailSideEffect()
    data class NavigateToConfirmedDetail(val groupId: Long, val confirmedId: Long) : GroupDetailSideEffect()
    data class NavigateToGroupMember(val groupId: Long) : GroupDetailSideEffect()
}
