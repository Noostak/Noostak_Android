package com.sopt.presentation.auth.signup.inputGroupCode

sealed class InputGroupCodeSideEffect {
    data object NavigateUp : InputGroupCodeSideEffect()
    data object NavigateToGroup : InputGroupCodeSideEffect()
}
