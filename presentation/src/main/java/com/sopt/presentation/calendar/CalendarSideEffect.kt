package com.sopt.presentation.calendar

sealed interface CalendarSideEffect {
    data object NavigateToGroupCreate : CalendarSideEffect
    data object NavigateToGroupEnter : CalendarSideEffect
    data class ShowAddDialog(val show: Boolean) : CalendarSideEffect
    data object ShowBottomSheet: CalendarSideEffect
}
