package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.AUTH
import com.sopt.data.service.ApiKeyStorage.LOGOUT
import com.sopt.data.service.ApiKeyStorage.V1
import com.sopt.data.service.ApiKeyStorage.WITHDRAW
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AccountApiService {
    @POST("/$API/$V1/$AUTH/$LOGOUT")
    suspend fun postLogout(): BaseResponse<Unit>

    @DELETE("/$API/$V1/$AUTH/$WITHDRAW")
    suspend fun deleteWithdraw(): BaseResponse<Unit>
}
