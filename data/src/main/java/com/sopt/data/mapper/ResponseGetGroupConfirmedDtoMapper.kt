package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetGroupConfirmedDto
import com.sopt.domain.entity.AppointmentTimeEntity
import com.sopt.domain.entity.ConfirmedAppointmentEntity
import com.sopt.domain.entity.GroupConfirmedAppointmentsEntity
import com.sopt.domain.entity.GroupConfirmedInfoEntity

fun ResponseGetGroupConfirmedDto.toGroupConfirmedAppointmentsEntity() = GroupConfirmedAppointmentsEntity(
    groupConfirmedInfo = GroupConfirmedInfoEntity(
        groupName = groupConfirmedInfo.groupName,
        groupProfileImageUrl = groupConfirmedInfo.groupProfileImageUrl,
        groupMemberCount = groupConfirmedInfo.groupMemberCount
    ),
    confirmedAppointments = confirmedAppointments.map {
        ConfirmedAppointmentEntity(
            appointmentId = it.appointmentId,
            appointmentOptionId = it.appointmentOptionId,
            appointmentName = it.appointmentName,
            category = it.category,
            appointmentTime = AppointmentTimeEntity(
                date = it.appointmentTime.date,
                startTime = it.appointmentTime.startTime,
                endTime = it.appointmentTime.endTime
            )
        )
    }
)
