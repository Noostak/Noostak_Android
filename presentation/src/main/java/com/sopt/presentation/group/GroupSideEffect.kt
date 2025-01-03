package com.sopt.presentation.group

sealed interface GroupSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : GroupSideEffect
}