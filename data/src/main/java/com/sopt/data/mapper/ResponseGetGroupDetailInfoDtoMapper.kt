package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetGroupMembersDto
import com.sopt.domain.entity.GroupDetailGroupHostInfoEntity
import com.sopt.domain.entity.GroupDetailGroupInfoEntity
import com.sopt.domain.entity.GroupDetailGroupMemberInfoEntity
import com.sopt.domain.entity.GroupDetailInfoEntity
import com.sopt.domain.entity.GroupDetailMyInfoEntity

fun ResponseGetGroupMembersDto.toGroupDetailInfoEntity() = GroupDetailInfoEntity(
    myInfo = GroupDetailMyInfoEntity(
        memberName = myInfo.memberName,
        memberProfileImageUrl = myInfo.memberProfileImageUrl
    ),
    groupInfo = GroupDetailGroupInfoEntity(
        groupHostInfo = GroupDetailGroupHostInfoEntity(
            memberName = groupInfo.groupHostInfo.memberName,
            memberProfileImageUrl = groupInfo.groupHostInfo.memberProfileImageUrl
        ),
        groupName = groupInfo.groupName,
        groupProfileImageUrl = groupInfo.groupProfileImageUrl,
        groupMemberCount = groupInfo.groupMemberCount,
        groupInvitationCode = groupInfo.groupInvitationCode,
        groupMemberInfo = groupInfo.groupMemberInfo.map {
            GroupDetailGroupMemberInfoEntity(
                memberName = it.memberName,
                memberProfileImageUrl = it.memberProfileImageUrl
            )
        }
    )
)
