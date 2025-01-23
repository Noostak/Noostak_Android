package com.sopt.domain.entity

data class AppointmentCreateEntity(
    val appointmentName: String,
    val category: String,
    val duration: Int,
    val appointmentAvailableTimes: List<AppointmentTime>
)
data class AppointmentTime(
    val date: String,
    val startTime: String,
    val endTime: String
)