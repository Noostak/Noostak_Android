package com.sopt.domain.entity

data class GroupEntity(
    val groupId: Long = -1,
    val groupName: String = "",
    val groupPersonnel: Long = 0,
    val newsImage: String? = null,
)