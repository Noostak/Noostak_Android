package com.sopt.domain.entity

data class ConfirmedEntity(
    val appointmentId: Long,
    val appointmentName: String,
    val date: String,
    val category: String
)
