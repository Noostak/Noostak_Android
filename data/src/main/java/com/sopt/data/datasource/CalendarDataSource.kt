package com.sopt.data.datasource

import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetCalendarDto

interface CalendarDataSource {
    suspend fun getCalendarAppointments(
        groupId: Long,
        year: Int,
        month: Int
    ): BaseResponse<ResponseGetCalendarDto>
}
