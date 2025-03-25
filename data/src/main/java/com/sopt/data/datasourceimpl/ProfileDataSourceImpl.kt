package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.ProfileDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetProfileDto
import com.sopt.data.service.ProfileApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(private val profileApiService: ProfileApiService) :
    ProfileDataSource {
    override suspend fun getProfile(): BaseResponse<ResponseGetProfileDto> {
        return profileApiService.getProfile()
    }

    override suspend fun patchProfile(
        memberName: RequestBody,
        memberProfileImage: MultipartBody.Part?
    ): BaseResponse<Unit> {
        return profileApiService.patchProfile(memberName, memberProfileImage)
    }
}
