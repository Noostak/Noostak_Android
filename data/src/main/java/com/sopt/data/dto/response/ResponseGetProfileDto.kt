package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetProfileDto(
    @SerialName("memberName") val memberName: String,
    @SerialName("memberProfileImage") val memberProfileImage: String? = null
)
