package com.sopt.domain.entity

data class TimeTableSelectedTimesEntity(
    val dates: List<String>,
    val startTime: String,
    val endTime: String
)
