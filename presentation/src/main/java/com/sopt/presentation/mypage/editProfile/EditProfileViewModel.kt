package com.sopt.presentation.mypage.editProfile

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.ProfileRepository
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<EditProfileSideEffect>() {
    private val _userProfileState = MutableStateFlow(ProfileEntity())
    val userProfileState: StateFlow<ProfileEntity> = _userProfileState

    private val _patchProfileState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Empty)

    fun patchProfile(memberName: String, memberProfileImage: String?) {
        viewModelScope.launch {
            _patchProfileState.emit(UiState.Loading)
            profileRepository.patchProfile(memberName, memberProfileImage)
                .onSuccess {
                    userInfoRepository.saveNickname(userProfileState.value.memberName)
                    userProfileState.value.memberProfileImage?.let { image ->
                        userInfoRepository.saveProfileImage(image)
                    }
                    _patchProfileState.emit(UiState.Success(it))
                    emitSideEffect(EditProfileSideEffect.NavigateToMyPage)
                }
                .onFailure { _patchProfileState.emit(UiState.Failure(it.message.toString())) }
        }
    }

    fun navigateUp() {
        emitSideEffect(EditProfileSideEffect.NavigateUp)
    }

    fun requestGalleryPicker() {
        if (_userProfileState.value.isPermissionGranted) {
            emitSideEffect(EditProfileSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(EditProfileSideEffect.ShowGallerySnackBar)
        }
    }

    fun updateGalleryPermissionState(isGranted: Boolean) {
        _userProfileState.update { it.copy(isPermissionGranted = isGranted) }
    }

    fun onImageSelected(imageUri: String?) {
        viewModelScope.launch {
            _userProfileState.update { it.copy(memberProfileImage = imageUri) }
        }
    }

    fun onMemberNameChanged(memberName: String) {
        _userProfileState.update { it.copy(memberName = memberName) }
        validateMemberName(memberName)
    }

    private fun validateMemberName(memberName: String) {
        viewModelScope.launch {
            _userProfileState.update {
                it.copy(
                    isMemberNameCheck = (
                        !memberName.isNullOrBlank() && memberName.length in 1..10 && memberName.all { name ->
                            name.isLetterOrDigit()
                        }
                        )
                )
            }
        }
    }

    fun validateProfile(memberName: String, memberProfileImage: String?): Boolean {
        return (memberName != userProfileState.value.memberName || memberProfileImage != userProfileState.value.memberProfileImage)
    }
}
