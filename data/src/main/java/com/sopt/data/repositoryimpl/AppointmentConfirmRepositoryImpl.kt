package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.AppointmentConfirmDataSource
import com.sopt.data.mapper.toAppointmentDetailEntity
import com.sopt.data.mapper.toAppointmentEntity
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import javax.inject.Inject

class AppointmentConfirmRepositoryImpl @Inject constructor(
    private val appointmentConfirmDataSource: AppointmentConfirmDataSource
) : AppointmentConfirmRepository {
    override suspend fun postLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.postLike(groupId, appointmentId, optionId)
        }
    }

    override suspend fun deleteLike(
        groupId: Long,
        appointmentId: Long,
        optionId: Long
    ): Result<Unit> {
        return runCatching {
            appointmentConfirmDataSource.deleteLike(groupId, appointmentId, optionId)
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
}
