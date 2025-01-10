package com.sopt.presentation.auth.signup.checkInvite

sealed class CheckInviteSideEffect {
    data object NavigateToGroup : CheckInviteSideEffect()
    data object NavigateToInputGroupCode : CheckInviteSideEffect()
}
