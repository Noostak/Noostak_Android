package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetConfirmedDto
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseLikesDto
import com.sopt.data.service.AppointmentConfirmApiService
import javax.inject.Inject

class AppointmentConfirmDataSourceImpl @Inject constructor(
    private val appointmentConfirmApiService: AppointmentConfirmApiService
) : AppointmentConfirmDataSource {
    override suspend fun postLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): BaseResponse<ResponseLikesDto> {
        return appointmentConfirmApiService.postLike(groupId, appointmentId, optionId)
    }

    override suspend fun deleteLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): BaseResponse<ResponseLikesDto> {
        return appointmentConfirmApiService.deleteLike(groupId, appointmentId, optionId)
    }

    override suspend fun getOptions(
        appointmentId: Long
    ): BaseResponse<ResponseGetOptionsDto> {
        return appointmentConfirmApiService.getOptions(appointmentId)
    }

    override suspend fun getConfirmed(appointmentOptionId: Long): BaseResponse<ResponseGetConfirmedDto> {
        return appointmentConfirmApiService.getConfirmed(appointmentOptionId)
    }

    override suspend fun postConfirmed(appointmentOptionId: Long): BaseResponse<Unit> {
        return appointmentConfirmApiService.postConfirmed(appointmentOptionId)
    }
}
