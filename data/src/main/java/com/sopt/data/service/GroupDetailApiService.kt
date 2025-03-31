package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetGroupConfirmedDto
import com.sopt.data.dto.response.ResponseGetGroupMembersDto
import com.sopt.data.dto.response.ResponseGetGroupOngoingDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.APPOINTMENTS
import com.sopt.data.service.ApiKeyStorage.CONFIRMED
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.MEMBERS
import com.sopt.data.service.ApiKeyStorage.ONGOING
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupDetailApiService {
    @GET("/$API/$V1/$GROUPS/{$GROUP_ID}/$MEMBERS")
    suspend fun getGroupDetailInfo(
        @Path(GROUP_ID) groupId: Long
    ): BaseResponse<ResponseGetGroupMembersDto>

    @GET("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS/$ONGOING")
    suspend fun getGroupOngoingAppointments(
        @Path("groupId") groupId: Long
    ): BaseResponse<ResponseGetGroupOngoingDto>

    @GET("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS/$CONFIRMED")
    suspend fun getGroupConfirmedAppointments(
        @Path("groupId") groupId: Long
    ): BaseResponse<ResponseGetGroupConfirmedDto>
}
