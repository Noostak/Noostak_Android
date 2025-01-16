package com.sopt.presentation.mypage.editProfile

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : BaseViewModel<EditProfileSideEffect>() {
    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState: StateFlow<EditProfileState> = _editProfileState

    private val _userInfoState = MutableStateFlow(UserEntity())
    val userInfoState: StateFlow<UserEntity> = _userInfoState


    fun updateGalleryPermissionState(isGranted: Boolean) {
        viewModelScope.launch {
            _editProfileState.update { it.copy(isPermissionGranted = isGranted) }
        }
    }

    fun requestGalleryPicker() {
        if (_editProfileState.value.isPermissionGranted) {
            emitSideEffect(EditProfileSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(EditProfileSideEffect.ShowGalleryToast)
        }
    }

    fun onImageSelected(imageUri: String?) {
        viewModelScope.launch {
            _userInfoState.update { it.copy(profileImage = imageUri) }
        }
    }

    fun onUserNameChanged(nickName: String) {
        _userInfoState.update { it.copy(nickName = nickName) }
        validateGroupName(nickName)
    }

    private fun validateGroupName(userName: String) {
        viewModelScope.launch {
            _editProfileState.update { it.copy(isNameCheck = userName.length in 1..10) }
        }
    }
}
