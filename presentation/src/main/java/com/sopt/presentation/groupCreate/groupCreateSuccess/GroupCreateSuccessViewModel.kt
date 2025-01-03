package com.sopt.presentation.groupCreate.groupCreateSuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreateSuccessViewModel @Inject constructor() : ViewModel() {
    private val _sideEffect = MutableSharedFlow<GroupCreateSuccessSideEffect>()
    val sideEffect: SharedFlow<GroupCreateSuccessSideEffect> get() = _sideEffect.asSharedFlow()

    fun navigateToGroupDetail(groupId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(GroupCreateSuccessSideEffect.NavigateToGroupDetail(groupId))
        }
    }

    suspend fun onCodeCopyBtnClick() {
        _sideEffect.emit(GroupCreateSuccessSideEffect.ShowSnackBar(R.string.sb_group_create_success_code_copy))
    }
}
