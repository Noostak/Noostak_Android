package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseSocialLoginDto
import com.sopt.domain.entity.AuthEntity

fun ResponseSocialLoginDto.toAuthEntity() = AuthEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    memberId = memberId,
    authType = authType
)
