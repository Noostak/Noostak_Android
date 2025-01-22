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

    private var initialNickName: String? = null
    private var initialProfileImage: String? = null

    fun setInitialUserInfo(nickName: String, profileImage: String?) {
        initialNickName = nickName
        initialProfileImage = profileImage
        _userInfoState.update { it.copy(nickName = nickName, profileImage = profileImage) }
        validateChanges()
    }

    fun navigateUp() {
        emitSideEffect(EditProfileSideEffect.NavigateUp)
    }

    fun navigateToMyPage() {
        saveNickName(_userInfoState.value.nickName)
        emitSideEffect(EditProfileSideEffect.NavigateToMyPage)
    }

    fun onNickNameChanged(nickName: String) {
        _userInfoState.update { it.copy(nickName = nickName) }
        validateChanges()
    }

    private fun validateChanges() {
        val currentState = _userInfoState.value
        val isNameValid = validateNickName(currentState.nickName)
        val isChanged =
            currentState.nickName != initialNickName || currentState.profileImage != initialProfileImage

        _editProfileState.update { it.copy(isNameCheck = isNameValid && isChanged) }
    }

    private fun validateNickName(nickName: String?): Boolean {
        return !nickName.isNullOrBlank() && nickName.length in 1..10 && nickName.all { it.isLetterOrDigit() }
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
            validateChanges()
        }
    }

    private fun executeInScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
