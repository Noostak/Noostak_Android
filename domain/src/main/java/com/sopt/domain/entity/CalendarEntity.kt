package com.sopt.domain.entity

data class CalendarEntity(
    val appointmentName: String = "",
    val startDate: String? = null,
    val endDate: String? = null,
    val dates: List<String>? = null,
    val startTime: String = "",
    val endTime: String = "",
    val category: String = "",
    val time: Int = 0
)
