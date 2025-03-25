package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetProfileDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.PROFILE
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.GET

interface ProfileApiService {
    @GET("/$API/$V1/$PROFILE")
    suspend fun getProfile(): BaseResponse<ResponseGetProfileDto>
}
