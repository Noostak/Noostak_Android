package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseReissueTokenDto
import com.sopt.domain.entity.ReissueTokenEntity

fun ResponseReissueTokenDto.toReissueTokenEntity() = ReissueTokenEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    authType = authType
)
