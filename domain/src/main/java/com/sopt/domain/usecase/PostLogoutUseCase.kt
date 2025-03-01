package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class PostLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String) = authRepository.postLogout(accessToken)
}
