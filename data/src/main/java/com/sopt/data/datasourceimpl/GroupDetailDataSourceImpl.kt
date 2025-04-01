package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.GroupDetailDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupConfirmedDetailDto
import com.sopt.data.dto.response.ResponseGetGroupConfirmedDto
import com.sopt.data.dto.response.ResponseGetGroupMembersDto
import com.sopt.data.dto.response.ResponseGetGroupOngoingDto
import com.sopt.data.service.GroupDetailApiService
import javax.inject.Inject

class GroupDetailDataSourceImpl @Inject constructor(
    private val groupDetailApiService: GroupDetailApiService
) : GroupDetailDataSource {

    override suspend fun getGroupDetailInfo(groupId: Long): BaseResponse<ResponseGetGroupMembersDto> {
        return groupDetailApiService.getGroupDetailInfo(groupId)
    }

    override suspend fun getGroupOngoingAppointments(groupId: Long): BaseResponse<ResponseGetGroupOngoingDto> {
        return groupDetailApiService.getGroupOngoingAppointments(groupId)
    }

    override suspend fun getGroupConfirmedAppointments(groupId: Long): BaseResponse<ResponseGetGroupConfirmedDto> {
        return groupDetailApiService.getGroupConfirmedAppointments(groupId)
    }

    override suspend fun getConfirmedDetail(appointmentId: Long): BaseResponse<ResponseGetGroupConfirmedDetailDto> {
        return groupDetailApiService.getGroupConfirmedAppointment(appointmentId)
    }
}
