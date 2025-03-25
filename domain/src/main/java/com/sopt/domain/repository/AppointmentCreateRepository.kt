package com.sopt.domain.repository

import com.sopt.domain.entity.TimeEntity

interface AppointmentCreateRepository {
    suspend fun postAppointmentCreate(
        groupId: Long,
        appointmentName: String,
        category: String,
        duration: Int,
        appointmentHostSelectionTimes: List<TimeEntity>
    ): Result<Unit>
}
