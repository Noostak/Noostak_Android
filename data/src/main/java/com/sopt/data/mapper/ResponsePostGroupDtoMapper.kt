package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponsePostGroupDto
import com.sopt.domain.entity.GroupSuccessEntity

fun ResponsePostGroupDto.toGroupSuccessEntity() = GroupSuccessEntity(
    groupId = groupId,
    groupInvitationCode = groupInvitationCode
)
