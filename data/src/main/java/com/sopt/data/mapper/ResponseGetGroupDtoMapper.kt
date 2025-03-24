package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetGroupDto
import com.sopt.domain.entity.GroupEntity

fun ResponseGetGroupDto.toGroupEntity() = GroupEntity(
    groupId = groupId,
    groupName = groupName,
    groupMemberCount = groupMemberCount,
    groupProfileImageUrl = groupProfileImageUrl
)
