package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetProfileDto

interface ProfileDataSource {
    suspend fun getProfile(): BaseResponse<ResponseGetProfileDto>
}
