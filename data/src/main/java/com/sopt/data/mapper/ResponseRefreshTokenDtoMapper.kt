package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseRefreshTokenDto
import com.sopt.domain.entity.RefreshTokenEntity

fun ResponseRefreshTokenDto.toRefreshTokenEntity() = RefreshTokenEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    authId = authId,
    authType = authType,
    isMember = isMember
)
