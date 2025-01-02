package com.sopt.domain.entity

data class GroupDetailEntity(
    val groupName: String,
    val groupMembersCount: Int,
    val progressEntities: List<ProgressEntity>,
    val confirmedEntities: List<ConfirmedEntity>
)
