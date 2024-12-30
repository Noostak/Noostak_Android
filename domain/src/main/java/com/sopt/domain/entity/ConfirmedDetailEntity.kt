package com.sopt.domain.entity

data class ConfirmedDetailEntity (
    val appointName: String,
    val date: String,
    val weekday: String,
    val startTime: String,
    val endTime: String,
    val category: String,
    val likes: Int,
    val availableMembersCount: Int,
    val availableMembers: List<String>,
    val unavailableMembersCount: Int,
    val unavailableMembers: List<String>
)