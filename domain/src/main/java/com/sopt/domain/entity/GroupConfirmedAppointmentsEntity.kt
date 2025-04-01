package com.sopt.domain.entity

data class GroupConfirmedAppointmentsEntity(
    val groupConfirmedInfo: GroupConfirmedInfoEntity,
    val confirmedAppointments: List<ConfirmedAppointmentEntity>
)

data class GroupConfirmedInfoEntity(
    val groupName: String,
    val groupProfileImageUrl: String?,
    val groupMemberCount: Int
)

data class ConfirmedAppointmentEntity(
    val appointmentId: Long,
    val appointmentOptionId: Long,
    val appointmentName: String,
    val category: String,
    val appointmentTime: AppointmentTimeEntity
)
