package com.sopt.domain.usecase

import com.sopt.domain.repository.AccountRepository
import javax.inject.Inject

class DeleteWithdrawUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = accountRepository.deleteWithdraw()
}
