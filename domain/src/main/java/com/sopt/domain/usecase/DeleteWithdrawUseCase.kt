package com.sopt.domain.usecase

import com.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteWithdrawUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String) = authRepository.deleteWithdraw(accessToken)
}
