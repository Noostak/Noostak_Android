package com.sopt.data.dto.request

import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostAppointmentCreateDto(
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("category") val category: String,
    @SerialName("duration") val duration: Int,
    @SerialName("appointmentHostSelectionTimes") val appointmentHostSelectionTimes: List<BaseTimeDto>
)
