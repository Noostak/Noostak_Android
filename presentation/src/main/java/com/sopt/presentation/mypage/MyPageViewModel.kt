package com.sopt.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.ProfileRepository
import com.sopt.domain.repository.UserInfoRepository
import com.sopt.domain.usecase.DeleteWithdrawUseCase
import com.sopt.domain.usecase.PostLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val profileRepository: ProfileRepository,
    private val postLogoutUseCase: PostLogoutUseCase,
    private val deleteWithdrawUseCase: DeleteWithdrawUseCase
) : BaseViewModel<MyPageSideEffect>() {
    private val _userInfoState = MutableStateFlow(ProfileEntity())
    val userInfoState: StateFlow<ProfileEntity> = _userInfoState

    private val _getProfileState: MutableStateFlow<UiState<ProfileEntity>> =
        MutableStateFlow(UiState.Empty)

    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> get() = _showLogoutDialog

    private val _showWithdrawalDialog = MutableStateFlow(false)
    val showWithdrawalDialog: StateFlow<Boolean> get() = _showWithdrawalDialog

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _getProfileState.emit(UiState.Loading)
            profileRepository.getProfile().fold(onSuccess = { data ->
                _getProfileState.emit(UiState.Success(data))
                _userInfoState.update { it.copy(memberName = data.memberName) }
                _userInfoState.update { it.copy(memberProfileImage = data.memberProfileImage) }
            }, onFailure = { _getProfileState.emit(UiState.Failure(it.message.toString())) })
        }
    }

    private fun clearInfo() {
        viewModelScope.launch {
            userInfoRepository.clearAll()
        }
    }

    fun navigateToEditProfile() {
        emitSideEffect(
            MyPageSideEffect.NavigateToEditProfile(
                nickname = _userInfoState.value.memberName,
                profileImage = _userInfoState.value.memberProfileImage
            )
        )
    }

    fun showDialog(dialogType: DialogType, show: Boolean) {
        if (dialogType == DialogType.LOGOUT) {
            _showLogoutDialog.update { show }
        } else if (dialogType == DialogType.WITHDRAWAL) {
            _showWithdrawalDialog.update { show }
        }
    }

    fun triggerDialog(dialogType: DialogType) {
        if (dialogType == DialogType.LOGOUT) {
            emitSideEffect(MyPageSideEffect.ShowDialog(DialogType.LOGOUT))
        } else if (dialogType == DialogType.WITHDRAWAL) {
            emitSideEffect(MyPageSideEffect.ShowDialog(DialogType.WITHDRAWAL))
        }
    }

    // 로그아웃
    fun postLogout() {
        viewModelScope.launch {
            postLogoutUseCase().fold(
                onSuccess = {
                    clearInfo()
                    emitSideEffect(MyPageSideEffect.NavigateToLogin)
                },
                onFailure = { error ->
                    Timber.e("postLogout Failed: ${error.message}")
                }
            )
        }
    }

    // 회원탈퇴
    fun deleteWithdraw() {
        viewModelScope.launch {
            deleteWithdrawUseCase().fold(
                onSuccess = {
                    clearInfo()
                    emitSideEffect(MyPageSideEffect.NavigateToLogin)
                },
                onFailure = { error ->
                    Timber.e("deleteWithdraw Failed: ${error.message}")
                }
            )
        }
    }
}
