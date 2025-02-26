package com.sopt.domain.entity

data class TimeTableSelectedEntity(
    val appointmentMembersInfo: List<AppointmentMembersInfoEntity>
)

data class AppointmentMembersInfoEntity(
    val memberId: Int,
    val memberName: String,
    val appointmentMemberAvailableTimes: List<TimeEntity>
)

data class TimeEntity(
    val date: String,
    val startTime: String,
    val endTime: String
)
