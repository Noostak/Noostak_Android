package com.sopt.domain.entity

data class AppointmentEntity(
    val priority: Int,
    val recommendations: List<RecommendationEntity>
)

data class RecommendationEntity(
    val id: Long,
    val date: String,
    val startTime: String,
    val endTime: String,
    val likes: Int,
    val availableMembersCount: Int,
    val availableMembers: List<String>,
    val unavailableMembersCount: Int,
    val unavailableMembers: List<String>
)
