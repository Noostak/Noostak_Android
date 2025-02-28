package com.sopt.domain.repository

import com.sopt.domain.entity.AuthEntity
import com.sopt.domain.entity.AuthTypeEntity
import com.sopt.domain.entity.ReissueTokenEntity

interface AuthRepository {
    suspend fun postSocialLogin(
        token: String,
        request: AuthTypeEntity
    ): Result<AuthEntity>

    suspend fun postReissueToken(
        refreshToken: String
    ): Result<ReissueTokenEntity>

}
