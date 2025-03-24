package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupsDto
import com.sopt.data.dto.response.ResponsePostGroupDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface GroupDataSource {
    suspend fun getGroups(): BaseResponse<ResponseGetGroupsDto>
    suspend fun postGroup(
        groupName: RequestBody,
        groupProfileImage: MultipartBody.Part?
    ): BaseResponse<ResponsePostGroupDto>
}
