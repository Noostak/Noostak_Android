package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseSignUpDto
import com.sopt.domain.entity.AuthEntity

fun ResponseSignUpDto.toAuthEntity() = AuthEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    memberId = memberId,
    authType = authType
)
