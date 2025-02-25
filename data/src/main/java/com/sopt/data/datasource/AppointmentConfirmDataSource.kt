package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetOptionsDto

interface AppointmentConfirmDataSource {
    suspend fun getOptions(appointmentId: Long): BaseResponse<ResponseGetOptionsDto?>
}