package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetProfileDto
import com.sopt.domain.entity.ProfileEntity

fun ResponseGetProfileDto.toProfileEntity() = ProfileEntity(
    memberName = memberName,
    memberProfileImage = memberProfileImage
)
