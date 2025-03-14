package com.sopt.domain.entity

data class AppointmentEntity(
    val isHost: Boolean,
    val recommendationPriority: List<RecommendationPriorityEntity>
)

data class RecommendationPriorityEntity(
    val priority: Int,
    val options: List<OptionEntity>
)

data class OptionEntity(
    val id: Long,
    val totalMemberCount: Int,
    val myIdentity: IdentityEntity,
    val date: String,
    val startTime: String,
    val endTime: String,
    val likes: Int,
    val liked: Boolean,
    val availableMemberCount: Int,
    val availableMembers: List<String>,
    val unavailableMemberCount: Int,
    val unavailableMembers: List<String>
)
