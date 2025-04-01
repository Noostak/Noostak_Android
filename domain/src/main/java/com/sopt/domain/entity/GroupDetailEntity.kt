package com.sopt.domain.entity

data class GroupDetailEntity(
    val groupName: String,
    val groupImage: String,
    val groupMembersCount: Int,
    val progressEntities: List<ProgressEntity>,
    val confirmedEntities: List<ConfirmedEntity>
)

data class GroupOngoingAppointmentsEntity(
    val groupOngoingInfo: GroupOngoingInfoEntity,
    val ongoingAppointments: List<OngoingAppointmentEntity>
)

data class GroupOngoingInfoEntity(
    val groupName: String,
    val groupProfileImageUrl: String?,
    val groupMemberCount: Long,
    val groupInviteCode: String
)

data class OngoingAppointmentEntity(
    val appointmentId: Long,
    val appointmentName: String,
    val availableGroupMemberCount: Long,
    val appointmentTime: AppointmentTimeEntity
)

data class AppointmentTimeEntity(
    val date: String,
    val startTime: String,
    val endTime: String
)
