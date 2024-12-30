package com.sopt.domain.entity

data class GroupDetailEntity (
    val name: String,
    val memberCount: Int,
    val progressEntities: List<ProgressEntity>,
    val confirmedEntities: List<ConfirmedEntity>
)