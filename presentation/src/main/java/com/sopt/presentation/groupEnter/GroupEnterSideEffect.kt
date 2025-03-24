package com.sopt.presentation.groupEnter

sealed class GroupEnterSideEffect {
    data object NavigateUp : GroupEnterSideEffect()
    data object NavigateToGroup : GroupEnterSideEffect()
}
