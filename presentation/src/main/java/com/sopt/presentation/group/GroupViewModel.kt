package com.sopt.presentation.group

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.GroupEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor() : BaseViewModel<GroupSideEffect>() {
    private val _showFABDialog = MutableStateFlow(false)
    val showFABDialog: StateFlow<Boolean> get() = _showFABDialog

    fun showFABDialog(show: Boolean) {
        _showFABDialog.update { show }
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(GroupSideEffect.NavigateToGroupDetail(groupId))
    }

    fun navigateToGroupCreate() {
        emitSideEffect(GroupSideEffect.NavigateToGroupCreate)
    }

    fun navigateToGroupEnter() {
        emitSideEffect(GroupSideEffect.NavigateToGroupEnter)
    }

    val groupItems =
        listOf(
            GroupEntity(groupId = 1, groupName = "누스탁", groupPersonnel = 15, newsImage = null),
            GroupEntity(groupId = 2, groupName = "유니보이스", groupPersonnel = 16, newsImage = null),
            GroupEntity(groupId = 3, groupName = "솝트", groupPersonnel = 191, newsImage = null),
            GroupEntity(groupId = 4, groupName = "누스탁", groupPersonnel = 15, newsImage = null),
            GroupEntity(groupId = 5, groupName = "유니보이스", groupPersonnel = 16, newsImage = null),
            GroupEntity(groupId = 6, groupName = "솝트", groupPersonnel = 191, newsImage = null),
            GroupEntity(groupId = 7, groupName = "누스탁", groupPersonnel = 15, newsImage = null),
            GroupEntity(groupId = 8, groupName = "유니보이스", groupPersonnel = 16, newsImage = null),
            GroupEntity(groupId = 9, groupName = "솝트", groupPersonnel = 191, newsImage = null),
            GroupEntity(groupId = 10, groupName = "누스탁", groupPersonnel = 15, newsImage = null),
            GroupEntity(groupId = 11, groupName = "유니보이스", groupPersonnel = 16, newsImage = null),
            GroupEntity(groupId = 12, groupName = "솝트", groupPersonnel = 191, newsImage = null)
        )
}
