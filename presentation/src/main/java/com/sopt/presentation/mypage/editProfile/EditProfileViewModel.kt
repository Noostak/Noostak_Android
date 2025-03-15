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

    private var initialNickname: String? = null
    private var initialProfileImage: String? = null

    fun setInitialUserInfo(nickname: String, profileImage: String?) {
        initialNickname = nickname
        initialProfileImage = profileImage
        _userInfoState.update { it.copy(nickname = nickname, profileImage = profileImage) }
        validateChanges()
    }

    fun navigateUp() {
        emitSideEffect(EditProfileSideEffect.NavigateUp)
    }

    fun navigateToMyPage() {
        viewModelScope.launch {
            saveNickname(_userInfoState.value.nickname)
        }

        emitSideEffect(EditProfileSideEffect.NavigateToMyPage)
    }

    fun onNicknameChanged(nickname: String) {
        _userInfoState.update { it.copy(nickname = nickname) }
        validateChanges()
    }

    private fun validateChanges() {
        val currentState = _userInfoState.value
        val isNameValid = validateNickname(currentState.nickname)
        val isChanged =
            currentState.nickname != initialNickname || currentState.profileImage != initialProfileImage

        _editProfileState.update { it.copy(isNameCheck = isNameValid && isChanged) }
    }

    private fun validateNickname(nickname: String?): Boolean {
        return !nickname.isNullOrBlank() && nickname.length in 1..10 && nickname.all { it.isLetterOrDigit() }
    }

    private fun saveNickname(nickname: String) {
        viewModelScope.launch {
            userInfoRepository.saveNickname(nickname)
        }
    }

    fun updateGalleryPermissionState(isGranted: Boolean) {
        _editProfileState.update { it.copy(isPermissionGranted = isGranted) }
    }

    fun requestGalleryPicker() {
        if (_editProfileState.value.isPermissionGranted) {
            emitSideEffect(EditProfileSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(EditProfileSideEffect.ShowGallerySnackBar)
        }
    }

    fun updateProfileImage(imageUri: String?) {
        viewModelScope.launch {
            _userInfoState.update { it.copy(profileImage = imageUri) }
            imageUri?.let { userInfoRepository.saveProfileImage(it) }
            validateChanges()
        }
    }
}
