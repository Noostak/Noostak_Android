package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AppointmentCreateDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostAppointmentCreateDto
import com.sopt.data.service.AppointmentCreateApiService
import javax.inject.Inject

class AppointmentCreateDataSourceImpl @Inject constructor(
    private val appointmentApiService: AppointmentCreateApiService
) : AppointmentCreateDataSource {
    override suspend fun postAppointmentCreate(
        groupId: Long,
        request: RequestPostAppointmentCreateDto
    ): BaseResponse<Unit> {
        return appointmentApiService.postAppointment(groupId, request)
    }
}
