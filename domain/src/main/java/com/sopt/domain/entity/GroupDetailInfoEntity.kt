package com.sopt.domain.entity

data class GroupDetailInfoEntity(
    val myInfo: GroupDetailMyInfoEntity,
    val groupInfo: GroupDetailGroupInfoEntity
)

data class GroupDetailMyInfoEntity(
    val memberName: String,
    val memberProfileImageUrl: String?
)

data class GroupDetailGroupInfoEntity(
    val groupHostInfo: GroupDetailGroupHostInfoEntity,
    val groupName: String,
    val groupProfileImageUrl: String?,
    val groupMemberCount: Int,
    val groupInvitationCode: String,
    val groupMemberInfo: List<GroupDetailGroupMemberInfoEntity>
)

data class GroupDetailGroupHostInfoEntity(
    val memberName: String,
    val memberProfileImageUrl: String?
)

data class GroupDetailGroupMemberInfoEntity(
    val memberName: String,
    val memberProfileImageUrl: String?
)
