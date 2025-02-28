package com.sopt.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestRefreshTokenDto(
    @SerialName("code") val code: String,
    @SerialName("authType") val authType: String
)
