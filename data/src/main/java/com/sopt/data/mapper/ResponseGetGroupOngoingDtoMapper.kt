package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetGroupOngoingDto
import com.sopt.domain.entity.AppointmentTimeEntity
import com.sopt.domain.entity.GroupOngoingAppointmentsEntity
import com.sopt.domain.entity.GroupOngoingInfoEntity
import com.sopt.domain.entity.OngoingAppointmentEntity

fun ResponseGetGroupOngoingDto.toGroupOngoingAppointmentsEntity() = GroupOngoingAppointmentsEntity(
    groupOngoingInfo = GroupOngoingInfoEntity(
        groupName = groupOngoingInfo.groupName,
        groupProfileImageUrl = groupOngoingInfo.groupProfileImageUrl,
        groupMemberCount = groupOngoingInfo.groupMemberCount,
        groupInviteCode = groupOngoingInfo.groupInviteCode
    ),
    ongoingAppointments = ongoingAppointments.map {
        OngoingAppointmentEntity(
            appointmentId = it.appointmentId,
            appointmentName = it.appointmentName,
            availableGroupMemberCount = it.availableGroupMemberCount,
            appointmentTime = AppointmentTimeEntity(
                date = it.appointmentTime.date,
                startTime = it.appointmentTime.startTime,
                endTime = it.appointmentTime.endTime
            )
        )
    }
)
