package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetCalendarDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.APPOINTMENTS
import com.sopt.data.service.ApiKeyStorage.CALENDAR
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.MONTH
import com.sopt.data.service.ApiKeyStorage.V1
import com.sopt.data.service.ApiKeyStorage.YEAR
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CalendarApiService {
    @GET("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS/$CALENDAR")
    suspend fun getCalendarAppointments(
        @Path(GROUP_ID) groupId: Long,
        @Query(YEAR) year: Int,
        @Query(MONTH) month: Int
    ): BaseResponse<ResponseGetCalendarDto>
}
