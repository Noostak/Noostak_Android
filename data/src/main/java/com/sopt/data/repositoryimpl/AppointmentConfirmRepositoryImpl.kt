package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.mapper.toAppointmentEntity
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import javax.inject.Inject

class AppointmentConfirmRepositoryImpl @Inject constructor(
    private val appointmentConfirmDataSource: AppointmentConfirmDataSource
): AppointmentConfirmRepository {
    override suspend fun getOptions(appointmentId: Long): Result<AppointmentEntity?> {
        return runCatching {
            appointmentConfirmDataSource.getOptions(appointmentId).result?.toAppointmentEntity()
        }
    }
}