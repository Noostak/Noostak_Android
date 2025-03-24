package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupsDto(
    @SerialName("groups") val groups: List<ResponseGetGroupDto>
)

@Serializable
data class ResponseGetGroupDto(
    @SerialName("groupId") val groupId: Long,
    @SerialName("groupName") val groupName: String,
    @SerialName("groupMemberCount") val groupMemberCount: Int,
    @SerialName("groupProfileImageUrl") val groupProfileImageUrl: String? = null
)
