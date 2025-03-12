package com.sopt.domain.usecase

import com.sopt.domain.entity.AuthTypeEntity
import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class PostSocialLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String, authType: AuthTypeEntity) =
        authRepository.postSocialLogin(authCode, authType)
}
