package com.sopt.domain.repository

import com.sopt.domain.entity.AuthEntity
import com.sopt.domain.entity.AuthTypeEntity

interface AuthRepository {
    suspend fun postSocialLogin(
        token: String,
        request: AuthTypeEntity
    ): Result<AuthEntity>
}
