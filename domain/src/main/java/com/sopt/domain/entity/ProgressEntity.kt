package com.sopt.domain.entity

data class ProgressEntity(
    val appointmentId: Long,
    val appointmentName: String,
    val startDate: String,
    val endDate: String,
    val participants: Int,
    val maxParticipants: Int
)
