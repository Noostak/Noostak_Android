package com.sopt.domain.entity

data class GroupEntity(
    val groupId: Long = -1,
    val groupName: String = "",
    val groupMemberCount: Int = 0,
    val groupProfileImageUrl: String? = null
)
