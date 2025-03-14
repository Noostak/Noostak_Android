package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetConfirmedDto
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.IdentityEntity

fun ResponseGetConfirmedDto.toAppointmentDetailEntity() = AppointmentDetailEntity(
    isHost = isHost,
    myIdentity = myInfo?.toIdentityEntity() ?: IdentityEntity("unavailable", -1, "나"),
    date = appointmentTime.date,
    startTime = appointmentTime.startTime,
    endTime = appointmentTime.endTime,
    category = category,
    availableMembersCount = availableFriends?.count ?: 0,
    availableMembers = availableFriends?.names?.filterNotNull() ?: emptyList(),
    unavailableMembersCount = unavailableFriends?.count ?: 0,
    unavailableMembers = unavailableFriends?.names ?.filterNotNull() ?: emptyList()
)
