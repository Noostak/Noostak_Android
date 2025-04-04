package com.sopt.domain.repository

import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity

interface AppointmentConfirmRepository {
    suspend fun postLike(appointmentId: Long, appointmentOptionId: Long): Result<Unit>
    suspend fun deleteLike(appointmentId: Long, appointmentOptionId: Long): Result<Unit>
    suspend fun getOptions(appointmentId: Long): Result<AppointmentEntity>
    suspend fun getOptionDetail(appointmentOptionId: Long): Result<AppointmentDetailEntity>
    suspend fun postOptionConfirm(appointmentOptionId: Long): Result<Unit>
    suspend fun getTimeTable(appointmentId: Long): Result<TimeTableEntity>
    suspend fun postTimeTable(appointmentId: Long, availableTimes: List<TimeEntity>): Result<Unit>
}
