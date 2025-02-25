package com.sopt.data.dto.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseMyInfoDto(
    @SerialName("availability") val availability: String,
    @SerialName("position") val position: Int,
    @SerialName("name") val name: String
)
