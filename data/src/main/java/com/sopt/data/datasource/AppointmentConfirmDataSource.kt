package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.request.RequestPostTimeTableDto
import com.sopt.data.dto.response.ResponseGetOptionDetailDto
import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseGetTimeTableDto
import com.sopt.data.dto.response.ResponseLikesDto

interface AppointmentConfirmDataSource {
    suspend fun postLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): BaseResponse<ResponseLikesDto>

    suspend fun deleteLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): BaseResponse<ResponseLikesDto>

    suspend fun getOptions(appointmentId: Long): BaseResponse<ResponseGetOptionsDto>

    suspend fun getOptionDetail(appointmentOptionId: Long): BaseResponse<ResponseGetOptionDetailDto>

    suspend fun postOptionConfirm(appointmentOptionId: Long): BaseResponse<Unit>

    suspend fun getTimeTable(appointmentId: Long): BaseResponse<ResponseGetTimeTableDto>

    suspend fun postTimeTable(
        appointmentId: Long,
        requestPostTimeTableDto: RequestPostTimeTableDto
    ): BaseResponse<Unit>
}
