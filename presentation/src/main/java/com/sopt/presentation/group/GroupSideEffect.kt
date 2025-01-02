package com.sopt.presentation.group

sealed class GroupSideEffect {
    data class NavigateToGroupDetail(val id: Long) : GroupSideEffect()
}
