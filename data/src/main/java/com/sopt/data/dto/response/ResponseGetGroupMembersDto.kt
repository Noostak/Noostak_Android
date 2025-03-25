package com.sopt.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetGroupMembersDto(
    @SerialName("myInfo") val myInfo: ResponseGetGroupMyInfoDto,
    @SerialName("groupInfo") val groupInfo: ResponseGetGroupHostInfoDto
)

@Serializable
data class ResponseGetGroupMyInfoDto(
    @SerialName("memberName") val memberName: String,
    @SerialName("memberProfileImageUrl") val memberProfileImageUrl: String? = null
)

@Serializable
data class ResponseGetGroupHostInfoDto(
    @SerialName("groupHostInfo") val groupHostInfo: ResponseGetGroupDetailGroupHostInfoDto,
    @SerialName("groupName") val groupName: String,
    @SerialName("groupProfileImageUrl") val groupProfileImageUrl: String? = null,
    @SerialName("groupMemberCount") val groupMemberCount: Int,
    @SerialName("groupInvitationCode") val groupInvitationCode: String,
    @SerialName("groupMemberInfo") val groupMemberInfo: List<ResponseGetGroupDetailGroupMemberInfoDto>
)

@Serializable
data class ResponseGetGroupDetailGroupHostInfoDto(
    @SerialName("memberName") val memberName: String,
    @SerialName("memberProfileImageUrl") val memberProfileImageUrl: String? = null
)

@Serializable
data class ResponseGetGroupDetailGroupMemberInfoDto(
    @SerialName("memberName") val memberName: String,
    @SerialName("memberProfileImageUrl") val memberProfileImageUrl: String? = null
)
