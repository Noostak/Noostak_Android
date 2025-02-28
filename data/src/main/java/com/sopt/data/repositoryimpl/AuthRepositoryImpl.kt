package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AuthDataSource
import com.sopt.data.dto.request.RequestPostSocialLoginDto
import com.sopt.data.mapper.toReissueTokenEntity
import com.sopt.data.mapper.toUserEntity
import com.sopt.domain.entity.AuthEntity
import com.sopt.domain.entity.AuthTypeEntity
import com.sopt.domain.entity.ReissueTokenEntity
import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun postSocialLogin(
        token: String,
        request: AuthTypeEntity
    ): Result<AuthEntity> {
        return runCatching {
            val response = authDataSource.postSocialLogin(token, RequestPostSocialLoginDto(request.authType))
            response.result?.toUserEntity()
                ?: throw Exception("postSocialLogin failed")
        }
    }

    override suspend fun postReissueToken(
        refreshToken: String
    ): Result<ReissueTokenEntity> {
        return runCatching {
            val response = authDataSource.postReissueToken(refreshToken)
            response.result?.toReissueTokenEntity()
                ?: throw Exception("postReissueToken failed")
        }
    }
}
