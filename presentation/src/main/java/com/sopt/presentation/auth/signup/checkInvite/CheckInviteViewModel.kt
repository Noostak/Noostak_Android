package com.sopt.presentation.auth.signup.checkInvite

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckInviteViewModel @Inject constructor() : BaseViewModel<CheckInviteSideEffect>() {

    fun navigateToInputGroupCode() {
        emitSideEffect(CheckInviteSideEffect.NavigateToInputGroupCode)
    }

    fun navigateToGroup() {
        emitSideEffect(CheckInviteSideEffect.NavigateToGroup)
    }
}
