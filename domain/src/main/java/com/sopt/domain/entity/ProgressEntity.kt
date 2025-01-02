package com.sopt.domain.entity

data class ProgressEntity(
    val appointmentId: Int,
    val appointmentName: String,
    val date: String,
    val weekday: String,
    val startTime: String,
    val endTime: String,
    val participants: Int,
    val maxParticipants: Int
)
