package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.CalendarDataSource
import com.sopt.data.dto.BaseResponse
import com.sopt.data.dto.response.ResponseGetCalendarDto
import com.sopt.data.service.CalendarApiService
import javax.inject.Inject

class CalendarDataSourceImpl @Inject constructor(
    private val calendarApiService: CalendarApiService
) : CalendarDataSource {
    override suspend fun getCalendarAppointments(
        groupId: Long,
        year: Int,
        month: Int
    ): BaseResponse<ResponseGetCalendarDto> {
        return calendarApiService.getCalendarAppointments(groupId, year, month)
    }
}
