package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseFriendsDto
import com.sopt.data.dto.response.base.BaseMyInfoDto
import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetConfirmedDto(
    @SerialName("appointmentTime") val appointmentTime: BaseTimeDto,
    @SerialName("category") val category: String,
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("myInfo") val myInfo: BaseMyInfoDto?,
    @SerialName("availableFriends") val availableFriends: BaseFriendsDto?,
    @SerialName("unavailableFriends") val unavailableFriends: BaseFriendsDto?
)
