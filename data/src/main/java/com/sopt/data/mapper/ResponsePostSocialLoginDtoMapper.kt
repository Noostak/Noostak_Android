package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponsePostSocialLoginDto
import com.sopt.domain.entity.AuthEntity

fun ResponsePostSocialLoginDto.toUserEntity() = AuthEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    memberId = memberId,
    authType = authType
)
