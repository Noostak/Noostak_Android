package com.sopt.domain.entity

data class TimeTableEntity(
    val isAppointMemberTimeSet: Boolean,
    val appointmentSchedule: AppointmentScheduleEntity // appointmentSchedule 추가
)

data class AppointmentScheduleEntity(
    val appointmentHostSelectionTimes: List<TimeEntity>,
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
