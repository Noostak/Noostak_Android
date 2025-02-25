package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseFriendsDto
import com.sopt.data.dto.response.base.BaseMyInfoDto
import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetOptionsDto(
    @SerialName("isAppointmentHost") val isAppointmentHost: Boolean,
    @SerialName("priorities") val priorities: List<ResponseGetOptionsPrioritiesDto?>
)

@Serializable
data class ResponseGetOptionsPrioritiesDto(
    @SerialName("priority") val priority: Int,
    @SerialName("options") val options: List<ResponseGetOptionsOptionDto?>
)

@Serializable
data class ResponseGetOptionsOptionDto(
    @SerialName("optionId") val optionId: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("liked") val liked: Boolean,
    @SerialName("groupMemberCount") val groupMemberCount: Int,
    @SerialName("availableMemberCount") val availableMemberCount: Int,
    @SerialName("appointmentOptionTime") val appointmentOptionTime: BaseTimeDto,
    @SerialName("myInfo") val myInfo: BaseMyInfoDto,
    @SerialName("availableFriends") val availableFriends: BaseFriendsDto,
    @SerialName("unavailableFriends") val unavailableFriends: BaseFriendsDto
)
