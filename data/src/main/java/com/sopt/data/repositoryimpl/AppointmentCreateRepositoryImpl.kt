package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AppointmentCreateDataSource
import com.sopt.data.dto.request.RequestPostAppointmentCreateDto
import com.sopt.data.mapper.toBaseTimeDto
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.repository.AppointmentCreateRepository
import javax.inject.Inject

class AppointmentCreateRepositoryImpl @Inject constructor(
    private val appointmentCreateDataSource: AppointmentCreateDataSource
) : AppointmentCreateRepository {
    override suspend fun postAppointmentCreate(
        groupId: Long,
        appointmentName: String,
        category: String,
        duration: Int,
        appointmentHostSelectionTimes: List<TimeEntity>
    ): Result<Unit> {
        return runCatching {
            appointmentCreateDataSource.postAppointmentCreate(
                groupId,
                RequestPostAppointmentCreateDto(
                    appointmentName = appointmentName,
                    category = category,
                    duration = duration,
                    appointmentHostSelectionTimes = appointmentHostSelectionTimes.map { it.toBaseTimeDto() }
                )
            )
        }
    }
}
