package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponsePostReissueTokenDto
import com.sopt.domain.entity.ReissueTokenEntity

fun ResponsePostReissueTokenDto.toReissueTokenEntity() = ReissueTokenEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    authType = authType
)
