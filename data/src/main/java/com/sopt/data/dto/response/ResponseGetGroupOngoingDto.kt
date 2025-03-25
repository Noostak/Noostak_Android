package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupOngoingDto(
    @SerialName("groupOngoingInfo") val groupOngoingInfo: GroupOngoingInfoDto,
    @SerialName("ongoingAppointments") val ongoingAppointments: List<OngoingAppointmentDto>
)

@Serializable
data class GroupOngoingInfoDto(
    @SerialName("groupName") val groupName: String,
    @SerialName("groupProfileImageUrl") val groupProfileImageUrl: String? = null,
    @SerialName("groupMemberCount") val groupMemberCount: Long,
    @SerialName("groupInviteCode") val groupInviteCode: String
)

@Serializable
data class OngoingAppointmentDto(
    @SerialName("appointmentId") val appointmentId: Long,
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("availableGroupMemberCount") val availableGroupMemberCount: Long,
    @SerialName("appointmentTime") val appointmentTime: AppointmentTimeDto
)

@Serializable
data class AppointmentTimeDto(
    @SerialName("date") val date: String,
    @SerialName("startTime") val startTime: String,
    @SerialName("endTime") val endTime: String
)
