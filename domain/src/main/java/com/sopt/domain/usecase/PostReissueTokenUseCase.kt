package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class PostReissueTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String) =
        authRepository.postReissueToken(refreshToken)
}
