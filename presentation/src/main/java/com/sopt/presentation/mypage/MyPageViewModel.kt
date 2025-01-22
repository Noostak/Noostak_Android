package com.sopt.presentation.mypage

import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel<MyPageSideEffect>() {
    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> get() = _showLogoutDialog

    private val _showWithdrawalDialog = MutableStateFlow(false)
    val showWithdrawalDialog: StateFlow<Boolean> get() = _showWithdrawalDialog

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
