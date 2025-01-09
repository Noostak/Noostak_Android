package com.sopt.domain.entity

data class AppointmentEntity(
    val isSubmitted: Boolean,
    val priorities: List<PriorityEntity>
)

data class PriorityEntity(
    val priority: Int,
    val availableMembersCount: Int,
    val totalMembersCount: Int,
    val recommendations: List<RecommendationEntity>
)

data class RecommendationEntity(
    val id: Long,
    val date: String,
    val startTime: String,
    val endTime: String,
    val likes: Int,
    val isLiked: Boolean,
    val availableMembersCount: Int,
    val availableMembers: List<String>,
    val unavailableMembersCount: Int,
    val unavailableMembers: List<String>
)
