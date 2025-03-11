package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse

interface AccountDataSource {
    suspend fun postLogout(): BaseResponse<Unit>
    suspend fun deleteWithdraw(): BaseResponse<Unit>
}
