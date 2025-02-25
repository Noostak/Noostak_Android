package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetConfirmedDto
import com.sopt.domain.entity.AppointmentDetailEntity

fun ResponseGetConfirmedDto.toAppointmentDetailEntity() = AppointmentDetailEntity(
    isHost = isHost,
    myIdentity = myInfo.toIdentityEntity(),
    date = appointmentTime.date,
    startTime = appointmentTime.startTime,
    endTime = appointmentTime.endTime,
    category = category,
    availableMembersCount = availableFriends.count,
    availableMembers = availableFriends.names,
    unavailableMembersCount = unavailableFriends.count,
    unavailableMembers = unavailableFriends.names
)
