package com.sopt.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostSocialLoginDto(
    @SerialName("authType") val authType: String
)
