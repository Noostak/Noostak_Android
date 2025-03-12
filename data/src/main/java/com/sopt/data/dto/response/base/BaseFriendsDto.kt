package com.sopt.data.dto.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseFriendsDto(
    @SerialName("count") val count: Int,
    @SerialName("names") val names: List<String>
)
