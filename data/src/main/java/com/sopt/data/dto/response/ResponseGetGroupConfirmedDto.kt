package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupConfirmedDto(
    @SerialName("groupConfirmedInfo") val groupConfirmedInfo: GroupConfirmedInfoDto,
    @SerialName("confirmedAppointments") val confirmedAppointments: List<ConfirmedAppointmentDto>
)

@Serializable
data class GroupConfirmedInfoDto(
    @SerialName("groupName") val groupName: String,
    @SerialName("groupProfileImageUrl") val groupProfileImageUrl: String? = null,
    @SerialName("groupMemberCount") val groupMemberCount: Int
)

@Serializable
data class ConfirmedAppointmentDto(
    @SerialName("appointmentId") val appointmentId: Long,
    @SerialName("appointmentOptionId") val appointmentOptionId: Long,
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("category") val category: String,
    @SerialName("appointmentTime") val appointmentTime: BaseTimeDto
)
