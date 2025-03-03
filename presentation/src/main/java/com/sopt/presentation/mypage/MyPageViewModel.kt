package com.sopt.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ProfileEntity
import com.sopt.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel<MyPageSideEffect>() {
    private val _userInfoState = MutableStateFlow(ProfileEntity())
    val userInfoState: StateFlow<ProfileEntity> = _userInfoState

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
                _userInfoState.update { it.copy(memberName = newNickname) }
            }
        }
    }

    private fun loadProfileImage() {
        viewModelScope.launch {
            userInfoRepository.getProfileImage().collectLatest { newImageUrl ->
                _userInfoState.update { it.copy(memberProfileImage = newImageUrl) }
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
}
