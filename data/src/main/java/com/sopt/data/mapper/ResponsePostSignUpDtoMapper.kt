package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponsePostSignUpDto
import com.sopt.domain.entity.AuthEntity

fun ResponsePostSignUpDto.toAuthEntity() = AuthEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    memberId = memberId,
    authType = authType
)
