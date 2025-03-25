package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupConfirmedDto(
    @SerialName("groupConfirmedInfoResponse") val groupConfirmedInfo: GroupConfirmedInfoDto,
    @SerialName("confirmedAppointmentsResponse") val confirmedAppointments: List<ConfirmedAppointmentDto>
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
    @SerialName("appointmentTime") val appointmentTime: AppointmentTimeDto
)
