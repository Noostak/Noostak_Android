package com.sopt.domain.repository

import com.sopt.domain.entity.AppointmentEntity

interface AppointmentConfirmRepository {
    suspend fun getOptions(appointmentId: Long): Result<AppointmentEntity?>
}