package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostGroupCodeDto(
    @SerialName("groupId") val groupId: Long
)
