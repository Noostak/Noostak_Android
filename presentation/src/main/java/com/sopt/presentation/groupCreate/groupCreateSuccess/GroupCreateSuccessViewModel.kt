package com.sopt.presentation.groupCreate.groupCreateSuccess

import com.sopt.core.util.BaseViewModel
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupCreateSuccessViewModel @Inject constructor() :
    BaseViewModel<GroupCreateSuccessSideEffect>() {
    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(GroupCreateSuccessSideEffect.NavigateToGroupDetail(groupId))
    }

    fun onCodeCopyBtnClick() {
        emitSideEffect(GroupCreateSuccessSideEffect.ShowSnackBar(R.string.sb_group_create_success_code_copy))
    }
}
