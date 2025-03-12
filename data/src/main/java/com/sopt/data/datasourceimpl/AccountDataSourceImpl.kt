package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AccountDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.service.AccountApiService
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountApiService: AccountApiService
) : AccountDataSource {
    override suspend fun postLogout(): BaseResponse<Unit> {
        return accountApiService.postLogout()
    }

    override suspend fun deleteWithdraw(): BaseResponse<Unit> {
        return accountApiService.deleteWithdraw()
    }
}
