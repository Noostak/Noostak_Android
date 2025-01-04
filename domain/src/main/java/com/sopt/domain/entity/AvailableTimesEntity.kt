package com.sopt.domain.entity

data class AvailableTimesEntity(
    val date: String,
    val times: List<TimeEntity>
)

data class TimeEntity(
    val startTime: String,
    val endTime: String
)