package com.sopt.presentation.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.domain.entity.GroupEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor() : ViewModel() {
    private val _sideEffect = MutableSharedFlow<GroupSideEffect>()
    val sideEffect: SharedFlow<GroupSideEffect> get() = _sideEffect.asSharedFlow()

    fun navigateToDetail(groupId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(GroupSideEffect.NavigateToDetail(groupId))
        }
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
            GroupEntity(groupId = 12, groupName = "솝트", groupPersonnel = 191, newsImage = null),
        )
}
