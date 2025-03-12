package com.sopt.domain.repository

interface AccountRepository {
    suspend fun postLogout(): Result<Unit>
    suspend fun deleteWithdraw(): Result<Unit>
}
