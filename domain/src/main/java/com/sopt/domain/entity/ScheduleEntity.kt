package com.sopt.domain.entity

data class ScheduleEntity(
    val groupId: Long = -1,
    val date: String, // M월 D일 (E) - ex) 1월 13일 (월)
    val scheduleList: List<CalendarAppointmentEntity>
)
