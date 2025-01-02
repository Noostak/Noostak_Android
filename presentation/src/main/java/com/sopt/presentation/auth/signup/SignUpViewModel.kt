package com.sopt.presentation.auth.signup

import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel<SignUpSideEffect>() {

    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> get() = _state.asStateFlow()

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateProfileImage(profileImage: String) {
        _state.value = _state.value.copy(profileImage = profileImage)
    }

    fun updateAuthId(authId: String) {
        _state.value = _state.value.copy(authId = authId)
    }

    fun navigateToCheckInvite() {
        emitSideEffect(SignUpSideEffect.NavigateToCheckInvite)
    }
}