package com.sopt.presentation.groupDetail

sealed class GroupDetailSideEffect {
    data object NavigateUp : GroupDetailSideEffect()
    data class NavigateToCompleteDetail(val id: Long) : GroupDetailSideEffect()
}