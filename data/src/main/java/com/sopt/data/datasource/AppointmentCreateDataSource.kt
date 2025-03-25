package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostAppointmentCreateDto

interface AppointmentCreateDataSource {
    suspend fun postAppointmentCreate(
        groupId: Long,
        request: RequestPostAppointmentCreateDto
    ): BaseResponse<Unit>
}
