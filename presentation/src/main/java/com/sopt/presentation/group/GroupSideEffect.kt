package com.sopt.presentation.group

sealed interface GroupSideEffect {
    data class NavigateToDetail(val groupId: Long) : GroupSideEffect
}