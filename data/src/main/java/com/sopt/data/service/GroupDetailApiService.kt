package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupConfirmedDto
import com.sopt.data.dto.response.ResponseGetGroupMembersDto
import com.sopt.data.dto.response.ResponseGetGroupOngoingDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.MEMBERS
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupDetailApiService {
    @GET("/$API/$V1/$GROUPS/{$GROUP_ID}/$MEMBERS")
    suspend fun getGroupDetailInfo(
        @Path(GROUP_ID) groupId: Long
    ): BaseResponse<ResponseGetGroupMembersDto>

    @GET("/api/v1/groups/{groupId}/appointments/ongoing")
    suspend fun getGroupOngoingAppointments(
        @Path("groupId") groupId: Long
    ): BaseResponse<ResponseGetGroupOngoingDto>

    @GET("/api/v1/groups/{groupId}/appointments/confirmed")
    suspend fun getGroupConfirmedAppointments(
        @Path("groupId") groupId: Long
    ): BaseResponse<ResponseGetGroupConfirmedDto>
}
