package com.sopt.data.dto.request

import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostTimeTableDto(
    @SerialName("availableTimes") val availableTimes: List<BaseTimeDto>
)
