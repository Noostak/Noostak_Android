package com.sopt.domain.repository

import com.sopt.domain.entity.GroupConfirmedAppointmentsEntity
import com.sopt.domain.entity.GroupDetailInfoEntity
import com.sopt.domain.entity.GroupOngoingAppointmentsEntity

interface GroupDetailRepository {
    suspend fun getGroupInfoDetail(groupId: Long): Result<GroupDetailInfoEntity>
    suspend fun getGroupOngoingAppointments(groupId: Long): Result<GroupOngoingAppointmentsEntity>
    suspend fun getGroupConfirmedAppointments(groupId: Long): Result<GroupConfirmedAppointmentsEntity>
}
