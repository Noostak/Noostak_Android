package com.sopt.domain.entity

data class ConfirmedEntity(
    val appointmentId: Long,
    val appointmentName: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val category: String
)
