package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupConfirmedDto
import com.sopt.data.dto.response.ResponseGetGroupMembersDto
import com.sopt.data.dto.response.ResponseGetGroupOngoingDto

interface GroupDetailDataSource {
    suspend fun getGroupDetailInfo(groupId: Long): BaseResponse<ResponseGetGroupMembersDto>
    suspend fun getGroupOngoingAppointments(groupId: Long): BaseResponse<ResponseGetGroupOngoingDto>
    suspend fun getGroupConfirmedAppointments(groupId: Long): BaseResponse<ResponseGetGroupConfirmedDto>
}
