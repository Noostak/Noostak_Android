package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.service.AppointmentConfirmApiService
import javax.inject.Inject

class AppointmentConfirmDataSourceImpl @Inject constructor(
    private val appointmentConfirmApiService: AppointmentConfirmApiService
): AppointmentConfirmDataSource {
    override suspend fun getOptions(
        appointmentId: Long
    ): BaseResponse<ResponseGetOptionsDto?> {
        return appointmentConfirmApiService.getOptions(appointmentId)
    }
}