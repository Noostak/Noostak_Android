package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.GroupDetailDataSource
import com.sopt.data.mapper.toConfirmedDetailEntity
import com.sopt.data.mapper.toGroupConfirmedAppointmentsEntity
import com.sopt.data.mapper.toGroupDetailInfoEntity
import com.sopt.data.mapper.toGroupOngoingAppointmentsEntity
import com.sopt.domain.entity.ConfirmedDetailEntity
import com.sopt.domain.entity.GroupConfirmedAppointmentsEntity
import com.sopt.domain.entity.GroupDetailInfoEntity
import com.sopt.domain.entity.GroupOngoingAppointmentsEntity
import com.sopt.domain.repository.GroupDetailRepository
import javax.inject.Inject

class GroupDetailRepositoryImpl @Inject constructor(
    private val groupDetailDataSource: GroupDetailDataSource
) : GroupDetailRepository {

    override suspend fun getGroupInfoDetail(groupId: Long): Result<GroupDetailInfoEntity> {
        return runCatching {
            groupDetailDataSource.getGroupDetailInfo(groupId)
                .result?.toGroupDetailInfoEntity()
                ?: throw Exception("getGroupInfoDetail failed")
        }
    }

    override suspend fun getGroupOngoingAppointments(groupId: Long): Result<GroupOngoingAppointmentsEntity> {
        return runCatching {
            groupDetailDataSource.getGroupOngoingAppointments(groupId).result?.toGroupOngoingAppointmentsEntity()
                ?: throw Exception("getGroupOngoingAppointments failed")
        }
    }

    override suspend fun getGroupConfirmedAppointments(groupId: Long): Result<GroupConfirmedAppointmentsEntity> {
        return runCatching {
            groupDetailDataSource.getGroupConfirmedAppointments(groupId)
                .result?.toGroupConfirmedAppointmentsEntity()
                ?: throw Exception("getGroupConfirmedAppointments failed")
        }
    }

    override suspend fun getConfirmedDetail(appointmentId: Long): Result<ConfirmedDetailEntity> {
        return runCatching {
            groupDetailDataSource.getConfirmedDetail(appointmentId).result?.toConfirmedDetailEntity()
                ?: throw Exception("getConfirmedDetail failed")
        }
    }
}
