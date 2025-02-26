package com.sopt.domain.entity

data class TimeTableScheduleEntity(
    val dates: List<String>,
    val startTime: String,
    val endTime: String
)
