package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostTimeTableDto
import com.sopt.data.dto.response.ResponseGetOptionDetailDto
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseGetTimeTableDto
import com.sopt.data.dto.response.ResponseLikesDto
import com.sopt.data.service.AppointmentConfirmApiService
import javax.inject.Inject

class AppointmentConfirmDataSourceImpl @Inject constructor(
    private val appointmentConfirmApiService: AppointmentConfirmApiService
) : AppointmentConfirmDataSource {
    override suspend fun postLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): BaseResponse<ResponseLikesDto> {
        return appointmentConfirmApiService.postLike(appointmentId, appointmentOptionId)
    }

    override suspend fun deleteLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): BaseResponse<ResponseLikesDto> {
        return appointmentConfirmApiService.deleteLike(appointmentId, appointmentOptionId)
    }

    override suspend fun getOptions(
        appointmentId: Long
    ): BaseResponse<ResponseGetOptionsDto> {
        return appointmentConfirmApiService.getOptions(appointmentId)
    }

    override suspend fun getOptionDetail(appointmentOptionId: Long): BaseResponse<ResponseGetOptionDetailDto> {
        return appointmentConfirmApiService.getOptionDetail(appointmentOptionId)
    }

    override suspend fun postOptionConfirm(appointmentOptionId: Long): BaseResponse<Unit> {
        return appointmentConfirmApiService.postOptionConfirm(appointmentOptionId)
    }

    override suspend fun getTimeTable(appointmentId: Long): BaseResponse<ResponseGetTimeTableDto> {
        return appointmentConfirmApiService.getTimeTable(appointmentId)
    }

    override suspend fun postTimeTable(
        appointmentId: Long,
        requestPostTimeTableDto: RequestPostTimeTableDto
    ): BaseResponse<Unit> {
        return appointmentConfirmApiService.postTimeTable(appointmentId, requestPostTimeTableDto)
    }
}
