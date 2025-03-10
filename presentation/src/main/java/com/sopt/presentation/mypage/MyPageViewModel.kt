package com.sopt.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.UserEntity
import com.sopt.domain.repository.UserInfoRepository
import com.sopt.domain.usecase.DeleteWithdrawUseCase
import com.sopt.domain.usecase.PostLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val postLogoutUseCase: PostLogoutUseCase,
    private val deleteWithdrawUseCase: DeleteWithdrawUseCase
) : BaseViewModel<MyPageSideEffect>() {
    private val _userInfoState = MutableStateFlow(UserEntity())
    val userInfoState: StateFlow<UserEntity> = _userInfoState

    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> get() = _showLogoutDialog

    private val _showWithdrawalDialog = MutableStateFlow(false)
    val showWithdrawalDialog: StateFlow<Boolean> get() = _showWithdrawalDialog

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        loadNickname()
        loadProfileImage()
    }

    private fun loadNickname() {
        viewModelScope.launch {
            userInfoRepository.getNickname().collectLatest { newNickname ->
                _userInfoState.update { it.copy(nickname = newNickname) }
            }
        }
    }

    private fun loadProfileImage() {
        viewModelScope.launch {
            userInfoRepository.getProfileImage().collectLatest { newImageUrl ->
                _userInfoState.update { it.copy(profileImage = newImageUrl) }
            }
        }
    }

    fun clearInfo() {
        viewModelScope.launch {
            userInfoRepository.clearAll()
        }
    }

    fun navigateToEditProfile() {
        emitSideEffect(
            MyPageSideEffect.NavigateToEditProfile(
                nickname = _userInfoState.value.nickname,
                profileImage = _userInfoState.value.profileImage
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
