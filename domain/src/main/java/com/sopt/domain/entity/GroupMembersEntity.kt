package com.sopt.domain.entity

data class GroupMembersEntity(
    val groupName: String,
    val groupMemberCount: Int,
    val groupLeader: GroupLeaderEntity,
    val groupMembers: List<GroupMemberEntity>
)

data class GroupLeaderEntity(
    val groupLeaderName: String,
    val groupLeaderImage: String
)

data class GroupMemberEntity(
    val groupMemberName: String,
    val groupMemberImage: String
)
