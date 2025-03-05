package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.dto.request.RequestPostTimeTableDto
import com.sopt.data.mapper.toAppointmentDetailEntity
import com.sopt.data.mapper.toAppointmentEntity
import com.sopt.data.mapper.toBaseTimeDto
import com.sopt.data.mapper.toTimeTableEntity
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import javax.inject.Inject

class AppointmentConfirmRepositoryImpl @Inject constructor(
    private val appointmentConfirmDataSource: AppointmentConfirmDataSource
) : AppointmentConfirmRepository {
    override suspend fun postLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.postLike(appointmentId, appointmentOptionId)
        }
    }

    override suspend fun deleteLike(
        appointmentId: Long,
        appointmentOptionId: Long
    ): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.deleteLike(appointmentId, appointmentOptionId)
        }
    }

    override suspend fun getOptions(appointmentId: Long): Result<AppointmentEntity> {
        return runCatching {
            appointmentConfirmDataSource.getOptions(appointmentId).result?.toAppointmentEntity()
                ?: throw Exception("getOptions failed")
        }
    }

    override suspend fun getConfirmed(appointmentOptionId: Long): Result<AppointmentDetailEntity> {
        return runCatching {
            appointmentConfirmDataSource.getConfirmed(appointmentOptionId).result?.toAppointmentDetailEntity()
                ?: throw Exception("getConfirmed failed")
        }
    }

    override suspend fun postConfirmed(appointmentOptionId: Long): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.postConfirmed(appointmentOptionId)
        }
    }

    override suspend fun getTimeTable(appointmentId: Long): Result<TimeTableEntity> {
        return runCatching {
            appointmentConfirmDataSource.getTimeTable(appointmentId).result?.toTimeTableEntity()
                ?: throw Exception("getTimeTable failed")
        }
    }

    override suspend fun postTimeTable(
        appointmentId: Long,
        availableTimes: List<TimeEntity>
    ): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.postTimeTable(
                appointmentId,
                RequestPostTimeTableDto(availableTimes.map { it.toBaseTimeDto() })
            )
        }
    }
}
