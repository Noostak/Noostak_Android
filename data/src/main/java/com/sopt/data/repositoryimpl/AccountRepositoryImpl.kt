package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AccountDataSource
import com.sopt.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource
) : AccountRepository {

    override suspend fun postLogout(): Result<Unit> {
        return runCatching {
            accountDataSource.postLogout()
        }
    }

    override suspend fun deleteWithdraw(): Result<Unit> {
        return runCatching {
            accountDataSource.deleteWithdraw()
        }
    }
}
