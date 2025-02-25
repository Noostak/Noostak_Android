package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostTimeTableDto
import com.sopt.data.dto.response.ResponseGetConfirmedDto
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseGetTimeTableDto
import com.sopt.data.dto.response.ResponseLikesDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.APPOINTMENTS
import com.sopt.data.service.ApiKeyStorage.APPOINTMENT_ID
import com.sopt.data.service.ApiKeyStorage.APPOINTMENT_MEMBERS
import com.sopt.data.service.ApiKeyStorage.APPOINTMENT_OPTIONS
import com.sopt.data.service.ApiKeyStorage.APPOINTMENT_OPTION_ID
import com.sopt.data.service.ApiKeyStorage.CONFIRMED
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.LIKES
import com.sopt.data.service.ApiKeyStorage.OPTIONS
import com.sopt.data.service.ApiKeyStorage.OPTION_ID
import com.sopt.data.service.ApiKeyStorage.PROGRESS
import com.sopt.data.service.ApiKeyStorage.TIMETABLE
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.Body
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

    @GET("/$API/$V1/$APPOINTMENT_OPTIONS/{$APPOINTMENT_OPTION_ID}/$CONFIRMED")
    suspend fun getConfirmed(
        @Path(APPOINTMENT_OPTION_ID) appointmentOptionId: Long
    ): BaseResponse<ResponseGetConfirmedDto>

    @POST("/$API/$V1/$APPOINTMENT_OPTIONS/{$APPOINTMENT_OPTION_ID}/$CONFIRMED")
    suspend fun postConfirmed(
        @Path(APPOINTMENT_OPTION_ID) appointmentOptionId: Long
    ): BaseResponse<Unit>

    @GET("/$API/$V1/$APPOINTMENT_MEMBERS/{$APPOINTMENT_ID}/$TIMETABLE")
    suspend fun getTimeTable(
        @Path(APPOINTMENT_ID) appointmentId: Long
    ): BaseResponse<ResponseGetTimeTableDto>

    @POST("/$API/$V1/$APPOINTMENT_MEMBERS/{$APPOINTMENT_ID}/$TIMETABLE")
    suspend fun postTimeTable(
        @Path(APPOINTMENT_ID) appointmentId: Long,
        @Body requestPostTimeTableDto: RequestPostTimeTableDto
    ): BaseResponse<Unit>
}
