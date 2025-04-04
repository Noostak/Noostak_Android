package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetProfileDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.PROFILE
import com.sopt.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface ProfileApiService {
    @GET("/$API/$V1/$PROFILE")
    suspend fun getProfile(): BaseResponse<ResponseGetProfileDto>

    @Multipart
    @PATCH("/$API/$V1/$PROFILE")
    suspend fun patchProfile(
        @Part("memberName") memberName: RequestBody,
        @Part("profileImageUpdated") profileImageUpdated: RequestBody,
        @Part memberProfileImage: MultipartBody.Part?
    ): BaseResponse<Unit>
}
