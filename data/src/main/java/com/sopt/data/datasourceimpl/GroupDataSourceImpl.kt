package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.GroupDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupsDto
import com.sopt.data.dto.response.ResponsePostGroupDto
import com.sopt.data.service.GroupApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(private val groupApiService: GroupApiService) :
    GroupDataSource {
    override suspend fun getGroups(): BaseResponse<ResponseGetGroupsDto> {
        return groupApiService.getGroups()
    }

    override suspend fun postGroup(
        groupName: RequestBody,
        groupProfileImage: MultipartBody.Part?
    ): BaseResponse<ResponsePostGroupDto> {
        return groupApiService.postGroup(groupName, groupProfileImage)
    }
}
