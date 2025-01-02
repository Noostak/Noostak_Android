package com.sopt.domain.entity

data class CalendarEntity(
    val appointName: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val category: String = "",
    val time: Int = 0
)
