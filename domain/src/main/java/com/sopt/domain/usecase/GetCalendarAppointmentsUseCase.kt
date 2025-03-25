package com.sopt.domain.usecase

import com.sopt.domain.repository.CalendarRepository
import javax.inject.Inject

class GetCalendarAppointmentsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        groupId: Long,
        year: Int,
        month: Int
    ) = calendarRepository.getCalendarAppointments(groupId, year, month)
}
