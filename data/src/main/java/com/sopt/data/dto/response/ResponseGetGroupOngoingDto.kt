package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseTimeDto
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
    @SerialName("groupInvitationCode") val groupInvitationCode: String
)

@Serializable
data class OngoingAppointmentDto(
    @SerialName("appointmentId") val appointmentId: Long,
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("availableGroupMemberCount") val availableGroupMemberCount: Long,
    @SerialName("appointmentTime") val appointmentTime: BaseTimeDto
)