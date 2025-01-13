package com.sopt.presentation.groupDetail.groupMember

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.GroupLeaderEntity
import com.sopt.domain.entity.GroupMemberEntity
import com.sopt.domain.entity.GroupMembersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupMemberViewModel @Inject constructor() : BaseViewModel<GroupMemberSideEffect>() {
    fun navigateUp() {
        emitSideEffect(GroupMemberSideEffect.NavigateUp)
    }

    val mockGroupMembers = GroupMembersEntity(
        groupName = "누스탁",
        groupMemberCount = 15,
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
