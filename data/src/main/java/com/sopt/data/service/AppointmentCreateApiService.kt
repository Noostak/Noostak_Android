package com.sopt.data.service

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostAppointmentCreateDto
import com.sopt.data.service.ApiKeyStorage.API
import com.sopt.data.service.ApiKeyStorage.APPOINTMENTS
import com.sopt.data.service.ApiKeyStorage.GROUPS
import com.sopt.data.service.ApiKeyStorage.GROUP_ID
import com.sopt.data.service.ApiKeyStorage.V1
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentCreateApiService {
    @POST("/$API/$V1/$GROUPS/{$GROUP_ID}/$APPOINTMENTS")
    suspend fun postAppointment(
        @Path(GROUP_ID) groupId: Long,
        @Body requestPostAppointmentDto: RequestPostAppointmentCreateDto
    ): BaseResponse<Unit>
}
