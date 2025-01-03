package com.sopt.presentation.group

sealed interface GroupSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : GroupSideEffect
    data object NavigateToGroupCreate : GroupSideEffect
    data object NavigateToGroupEnter : GroupSideEffect
}