package com.sopt.presentation.groupEnter

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupEnterViewModel @Inject constructor() : BaseViewModel<GroupEnterSideEffect>() {

    fun navigateUp() {
        emitSideEffect(GroupEnterSideEffect.NavigateUp)
    }

    fun navigateToGroup() {
        emitSideEffect(GroupEnterSideEffect.NavigateToGroup)
    }
}
