package com.sopt.domain.entity

data class AppointmentDetailEntity(
    val appointmentName: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val category: String,
    val availableMembersCount: Int,
    val availableMembers: List<String>,
    val unavailableMembersCount: Int,
    val unavailableMembers: List<String>
)
