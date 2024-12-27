package com.sopt.domain.entity

data class GroupDetailEntity (
    val name: String,
    val memberCount: Int,
    val progress: List<ProgressEntity>,
    val complete: List<CompleteEntity>
)