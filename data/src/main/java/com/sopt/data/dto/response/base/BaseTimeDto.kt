package com.sopt.data.dto.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseTimeDto(
    @SerialName("date") val date: String,
    @SerialName("startTime") val startTime: String,
    @SerialName("endTime") val endTime: String
)