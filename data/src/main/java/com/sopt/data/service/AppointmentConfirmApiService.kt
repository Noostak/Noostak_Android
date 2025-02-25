package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseLikesDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.APPOINTMENTS
import com.sopt.data.service.ApiKeyStorage.APPOINTMENT_ID
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.LIKES
import com.sopt.data.service.ApiKeyStorage.OPTIONS
import com.sopt.data.service.ApiKeyStorage.OPTION_ID
import com.sopt.data.service.ApiKeyStorage.PROGRESS
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentConfirmApiService {
    @POST("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS/{$APPOINTMENT_ID}/$PROGRESS/$OPTIONS/{$OPTION_ID}/$LIKES")
    suspend fun postLike(
        @Path(GROUP_ID) groupId: Long,
        @Path(APPOINTMENT_ID) appointmentId: Long,
        @Path(OPTION_ID) optionId: Long
    ): BaseResponse<ResponseLikesDto>

    @DELETE("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS/{$APPOINTMENT_ID}/$PROGRESS/$OPTIONS/{$OPTION_ID}/$LIKES")
    suspend fun deleteLike(
        @Path(GROUP_ID) groupId: Long,
        @Path(APPOINTMENT_ID) appointmentId: Long,
        @Path(OPTION_ID) optionId: Long
    ): BaseResponse<ResponseLikesDto>

    @GET("/$API/$V1/$APPOINTMENTS/{$APPOINTMENT_ID}/$OPTIONS")
    suspend fun getOptions(
        @Path(APPOINTMENT_ID) appointmentId: Long
    ): BaseResponse<ResponseGetOptionsDto>

}