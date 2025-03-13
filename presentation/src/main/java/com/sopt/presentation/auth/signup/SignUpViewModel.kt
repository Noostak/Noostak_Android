package com.sopt.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AuthEntity
import com.sopt.domain.repository.UserInfoRepository
import com.sopt.domain.usecase.PostSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val postSignUpUseCase: PostSignUpUseCase
) : BaseViewModel<SignUpSideEffect>() {

    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> get() = _signUpState.asStateFlow()

    fun onNicknameChanged(nickname: String) {
        _signUpState.update { it.copy(nickname = nickname) }
        validateNickname(nickname)
    }

    private fun validateNickname(nickname: String) {
        val isValid =
            nickname.isNotBlank() && nickname.length in 1..10 && nickname.matches("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]+$".toRegex())
        _signUpState.update { it.copy(isNameCheck = isValid) }
    }

    fun updateGalleryPermissionState(isGranted: Boolean) {
        _signUpState.update { it.copy(isPermissionGranted = isGranted) }
    }

    fun requestGalleryPicker() {
        if (_signUpState.value.isPermissionGranted) {
            emitSideEffect(SignUpSideEffect.RequestImagePicker)
        } else {
            emitSideEffect(SignUpSideEffect.ShowSnackBar)
        }
    }

    fun isGalleryPermissionGranted(): Boolean {
        return _signUpState.value.isPermissionGranted
    }

    fun updateProfileImage(imageUri: String?) {
        executeInScope {
            _signUpState.update { it.copy(profileImageUri = imageUri) }
            imageUri?.let { userInfoRepository.saveProfileImage(it) }
        }
    }

    fun navigateToCheckInvite() {
        val nickname = _signUpState.value.nickname
        if (nickname.isNotEmpty()) {
            saveUserNickname(nickname)
            emitSideEffect(SignUpSideEffect.NavigateToCheckInvite(nickname))
        }

        viewModelScope.launch {
            userInfoRepository.saveIsAutoLogin(true)
        }
    }

    private fun saveUserNickname(nickname: String) {
        executeInScope {
            userInfoRepository.saveNickname(nickname)
        }
    }

    private fun executeInScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    fun postSignUp(
        accessToken: String,
        socialType: String
    ) {
        viewModelScope.launch {
            val memberName = _signUpState.value.nickname
            val profileImageUri = userInfoRepository.getProfileImage().firstOrNull()

            val memberProfileImage = profileImageUri?.let { uri ->
                uriToMultipartBody(uri)
            }

            val nameRequestBody = memberName.toRequestBody("text/plain".toMediaTypeOrNull())
            val authTypeRequestBody = socialType.toRequestBody("text/plain".toMediaTypeOrNull())

            postSignUpUseCase(
                accessToken = accessToken,
                memberName = nameRequestBody,
                memberProfileImage = memberProfileImage,
                authType = authTypeRequestBody
            ).fold(
                onSuccess = { authEntity ->
                    saveUserInfo(authEntity)
                    navigateToCheckInvite()
                },
                onFailure = { error ->
                    Timber.e("postSignUp Failed: ${error.message}")
                }
            )
        }
    }

    private fun uriToMultipartBody(filePath: String): MultipartBody.Part? {
        val file = File(filePath)
        return if (file.exists()) {
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("memberProfileImage", file.name, requestBody)
        } else {
            null
        }
    }

    private fun saveUserInfo(authEntity: AuthEntity) {
        viewModelScope.launch {
            userInfoRepository.saveAccessToken("Bearer ${authEntity.accessToken}")
            userInfoRepository.saveRefreshToken("Bearer ${authEntity.refreshToken}")
            userInfoRepository.saveMemberId(authEntity.memberId)
            userInfoRepository.saveIsAutoLogin(true)
        }
    }
}
