package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class PostRefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String, authType: String) =
        authRepository.postRefreshToken(authCode, authType)
}
