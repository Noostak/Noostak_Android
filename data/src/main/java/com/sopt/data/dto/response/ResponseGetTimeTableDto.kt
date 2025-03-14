package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetTimeTableDto(
    @SerialName("isAppointMemberTimeSet") val isAppointmentMemberTimeSet: Boolean,
    @SerialName("appointmentScheduleResponse") val appointmentSchedule: ResponseAppointmentScheduleDto
)

@Serializable
data class ResponseAppointmentScheduleDto(
    @SerialName("appointmentHostSelectionTimesResponse") val appointmentHostSelectionTimesWrapper: AppointmentHostSelectionTimesWrapper,
    @SerialName("appointmentMemberInfoResponse") val appointmentMembersInfo: List<ResponseAppointmentMembersInfoDto?>
)

@Serializable
data class AppointmentHostSelectionTimesWrapper(
    @SerialName("appointmentHostSelectionTimeResponses") val appointmentHostSelectionTimes: List<BaseTimeDto>
)

@Serializable
data class ResponseAppointmentMembersInfoDto(
    @SerialName("memberId") val memberId: Int,
    @SerialName("memberName") val memberName: String,
    @SerialName("appointmentMemberAvailableTimesResponse") val appointmentMemberAvailableTimesWrapper: AppointmentMemberAvailableTimesWrapper
)

@Serializable
data class AppointmentMemberAvailableTimesWrapper(
    @SerialName("appointmentMemberAvailableTimesResponseResponse") val appointmentMemberAvailableTimes: List<BaseTimeDto>
)
