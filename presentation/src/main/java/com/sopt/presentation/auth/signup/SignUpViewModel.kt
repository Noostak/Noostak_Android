package com.sopt.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel<SignUpSideEffect>() {

    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> get() = _signUpState.asStateFlow()

    fun onUserNameChanged(userName: String) {
        _signUpState.update { it.copy(userName = userName) }
        validateUserName(userName)
    }

    private fun validateUserName(userName: String) {
        viewModelScope.launch {
            _signUpState.update { it.copy(isNameCheck = userName.length in 1..10) }
        }
    }

    fun updateGalleryPermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            _signUpState.update { it.copy(isPermissionGranted = isGranted) }
        }
    }

    fun requestGalleryPicker() {
        if (_signUpState.value.isPermissionGranted) {
            emitSideEffect(SignUpSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(SignUpSideEffect.ShowPermissionDeniedDialog)
        }
    }

    fun isGalleryPermissionGranted(): Boolean {
        return _signUpState.value.isPermissionGranted
    }

    fun updateProfileImage(imageUri: String?) {
        viewModelScope.launch {
            _signUpState.update { it.copy(profileImageUri = imageUri) }
            imageUri?.let { userInfoRepository.saveProfileImage(it) }
        }
    }

    fun navigateToCheckInvite() {
        val name = _signUpState.value.userName
        if (name.isNotEmpty()) {
            saveUserNickName(name)
            emitSideEffect(SignUpSideEffect.NavigateToCheckInvite(name))
        }
    }

    private fun saveUserNickName(nickName: String) {
        viewModelScope.launch {
            userInfoRepository.saveNickName(nickName)
        }
    }
}
