package com.sopt.data.dto.response

import com.sopt.data.dto.response.base.BaseTimeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupConfirmedDetailDto(
    @SerialName("appointmentTime") val appointmentTime: BaseTimeDto,
    @SerialName("category") val category: String,
    @SerialName("appointmentName") val appointmentName: String,
    @SerialName("myInfo") val myInfo: MyInfoDto,
    @SerialName("availableFriends") val availableFriends: FriendsDto,
    @SerialName("unavailableFriends") val unavailableFriends: FriendsDto
)

@Serializable
data class MyInfoDto(
    @SerialName("availability") val availability: String,
    @SerialName("position") val position: Int,
    @SerialName("name") val name: String
)

@Serializable
data class FriendsDto(
    @SerialName("count") val count: Int,
    @SerialName("names") val names: List<String>
)
