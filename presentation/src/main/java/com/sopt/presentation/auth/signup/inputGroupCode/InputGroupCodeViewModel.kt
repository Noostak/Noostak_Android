package com.sopt.presentation.auth.signup.inputGroupCode

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputGroupCodeViewModel @Inject constructor() : BaseViewModel<InputGroupCodeSideEffect>() {

    fun navigateUp() {
        emitSideEffect(InputGroupCodeSideEffect.NavigateUp)
    }

    fun navigateToGroup() {
        emitSideEffect(InputGroupCodeSideEffect.NavigateToGroup)
    }
}
