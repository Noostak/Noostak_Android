package com.sopt.presentation.mypage.editProfile

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.UserEntity
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel<EditProfileSideEffect>() {

    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState: StateFlow<EditProfileState> = _editProfileState

    private val _userInfoState = MutableStateFlow(UserEntity())
    val userInfoState: StateFlow<UserEntity> = _userInfoState

    fun navigateUp() {
        emitSideEffect(EditProfileSideEffect.NavigateUp)
    }

    fun navigateToMyPage() {
        saveNickName(_userInfoState.value.nickName)
        emitSideEffect(EditProfileSideEffect.NavigateToMyPage)
    }

    fun onNickNameChanged(nickName: String) {
        _userInfoState.update { it.copy(nickName = nickName) }
        validateNickName(nickName)
    }

    private fun validateNickName(nickName: String) {
        _editProfileState.update { it.copy(isNameCheck = nickName.length in 1..10) }
    }

    private fun saveNickName(nickName: String) {
        executeInScope {
            userInfoRepository.saveNickName(nickName)
        }
    }

    fun updateGalleryPermissionState(isGranted: Boolean) {
        _editProfileState.update { it.copy(isPermissionGranted = isGranted) }
    }

    fun requestGalleryPicker() {
        if (_editProfileState.value.isPermissionGranted) {
            emitSideEffect(EditProfileSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(EditProfileSideEffect.ShowGalleryToast)
        }
    }

    fun updateProfileImage(imageUri: String?) {
        executeInScope {
            _userInfoState.update { it.copy(profileImage = imageUri) }
            imageUri?.let { userInfoRepository.saveProfileImage(it) }
        }
    }

    private fun executeInScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
