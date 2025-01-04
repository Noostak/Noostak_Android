package com.sopt.domain.entity

data class TimeTableEntity(
    val startTime: String,
    val endTime: String,
    val timeEntity: List<TimeEntity>
)

data class TimeEntity(
    val date: String,
    val times: List<AvailableTimeEntity>? = null
)

data class AvailableTimeEntity(
    val startTime: String,
    val endTime: String,
    val level: Int? = null
)
