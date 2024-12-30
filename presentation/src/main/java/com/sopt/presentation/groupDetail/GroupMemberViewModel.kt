package com.sopt.presentation.groupDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.domain.entity.GroupLeaderEntity
import com.sopt.domain.entity.GroupMemberEntity
import com.sopt.domain.entity.GroupMembersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupMemberViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects: MutableSharedFlow<GroupMemberSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<GroupMemberSideEffect> get() = _sideEffects.asSharedFlow()

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(GroupMemberSideEffect.NavigateUp)
        }
    }

    val mockGroupMembers = GroupMembersEntity(
        groupName = "누스탁",
        groupMembersCount = 15,
        groupLeader = GroupLeaderEntity(
            groupLeaderName = "채영",
            groupLeaderImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
        ),
        groupMembers = listOf(
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = ""
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = ""
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = ""
            ),
            GroupMemberEntity(
                groupMemberName = "이가을",
                groupMemberImage = ""
            )
        )
    )
}

sealed class GroupMemberSideEffect {
    data object NavigateUp : GroupMemberSideEffect()
}
