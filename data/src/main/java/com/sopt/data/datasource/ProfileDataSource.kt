package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetProfileDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileDataSource {
    suspend fun getProfile(): BaseResponse<ResponseGetProfileDto>
    suspend fun patchProfile(
        memberName: RequestBody,
        profileImageUpdated: RequestBody,
        memberProfileImage: MultipartBody.Part?
    ): BaseResponse<Unit>
}
