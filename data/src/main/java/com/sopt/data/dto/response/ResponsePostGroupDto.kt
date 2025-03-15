package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostGroupDto(
    @SerialName("groupId") val groupId: Long,
    @SerialName("groupName") val groupName: String,
    @SerialName("groupProfileImageUrl") val groupProfileImageUrl: String? = null,
    @SerialName("groupInviteCode") val groupInviteCode: String
)
