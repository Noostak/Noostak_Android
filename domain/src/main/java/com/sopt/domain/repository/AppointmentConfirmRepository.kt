package com.sopt.domain.repository

import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.AppointmentEntity

interface AppointmentConfirmRepository {
    suspend fun postLike(groupId: Long, appointmentId: Long, optionId: Long): Result<Unit>
    suspend fun deleteLike(groupId: Long, appointmentId: Long, optionId: Long): Result<Unit>
    suspend fun getOptions(appointmentId: Long): Result<AppointmentEntity>
    suspend fun getConfirmed(appointmentOptionId: Long): Result<AppointmentDetailEntity>
    suspend fun postConfirmed(appointmentOptionId: Long): Result<Unit>
}