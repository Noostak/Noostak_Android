package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseLikesDto

interface AppointmentConfirmDataSource {
    suspend fun postLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): BaseResponse<ResponseLikesDto>

    suspend fun deleteLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): BaseResponse<ResponseLikesDto>

    suspend fun getOptions(appointmentId: Long): BaseResponse<ResponseGetOptionsDto>
}