package com.sopt.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.UserEntity
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
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
        executeInScope {
            loadNickName()
            loadProfileImage()
        }
    }

    private suspend fun loadNickName() {
        val nickName = userInfoRepository.getNickName().first()
        _userInfoState.update { it.copy(nickName = nickName) }
    }

    private suspend fun loadProfileImage() {
        val profileImageUrl = userInfoRepository.getProfileImage().first()
        _userInfoState.update { it.copy(profileImage = profileImageUrl) }
    }

    fun navigateToEditProfile() {
        emitSideEffect(
            MyPageSideEffect.NavigateToEditProfile(
                nickName = _userInfoState.value.nickName,
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

    private fun executeInScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
