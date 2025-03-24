package com.sopt.domain.repository

import com.sopt.domain.entity.CalendarEntity

interface CalendarRepository {
    suspend fun getCalendarAppointments(
        groupId: Long,
        year: Int,
        month: Int
    ): Result<CalendarEntity>
}
