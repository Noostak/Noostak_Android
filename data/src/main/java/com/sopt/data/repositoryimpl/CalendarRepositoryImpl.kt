package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.CalendarDataSource
import com.sopt.data.mapper.toCalendarEntity
import com.sopt.domain.entity.CalendarEntity
import com.sopt.domain.repository.CalendarRepository
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource
) : CalendarRepository {
    override suspend fun getCalendarAppointments(
        groupId: Long,
        year: Int,
        month: Int
    ): Result<CalendarEntity> {
        return runCatching {
            calendarDataSource.getCalendarAppointments(
                groupId,
                year,
                month
            ).result?.toCalendarEntity()
                ?: throw Exception("getCalendarAppointments failed")
        }
    }
}
