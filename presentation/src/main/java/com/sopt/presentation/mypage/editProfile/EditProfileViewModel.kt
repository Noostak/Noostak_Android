package com.sopt.presentation.mypage.editProfile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.ProfileRepository
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
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
    val patchProfileState: StateFlow<UiState<Unit>> get() = _patchProfileState

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    suspend fun urlToContentUri(context: Context, imageUrl: String): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Glide를 사용하여 Bitmap 다운로드
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()

                // 2. Bitmap을 File로 저장 (기존 파일 삭제 후 덮어쓰기)
                val tempFile = File(context.cacheDir, "downloaded_image.jpg")
                tempFile.outputStream().use { output ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                }

                // 3. FileProvider를 통해 content:// URI로 변환
                val authority = "${context.packageName}.provider"
                FileProvider.getUriForFile(context, authority, tempFile)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

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
                .onFailure {
                    _patchProfileState.emit(UiState.Failure(it.message.toString()))
                    triggerErrorDialog()
                }
        }
    }

    fun showErrorDialog(show: Boolean) {
        _showErrorDialog.update { show }
    }

    private fun triggerErrorDialog() {
        emitSideEffect(EditProfileSideEffect.ShowErrorDialog)
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
                    isMemberNameCheck = (memberName.isNotBlank() && memberName.length in 1..10 && memberName.all { name -> name.isLetterOrDigit() })
                )
            }
        }
    }

    fun validateProfile(memberName: String, memberProfileImage: String?): Boolean {
        return (memberName != userProfileState.value.memberName || memberProfileImage != userProfileState.value.memberProfileImage)
    }
}
