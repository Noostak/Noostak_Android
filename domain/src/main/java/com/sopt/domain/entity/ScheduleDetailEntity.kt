package com.sopt.domain.entity

data class ScheduleDetailEntity(
    val myIdentity: IdentityEntity,
    val appointmentName: String = "",
    val date: String = "", // YYYY-MM-DDTHH:mm:ss - ex) 2024-09-07T00:00:00
    val startTime: String = "", // YYYY-MM-DDTHH:mm:ss - ex) 2024-09-07T00:00:00
    val endTime: String = "", // YYYY-MM-DDTHH:mm:ss - ex) 2024-09-07T00:00:00
    val category: String = "",
    val availableMembersCount: Int = 0,
    val availableMembers: List<String>,
    val unavailableMembersCount: Int = 0,
    val unavailableMembers: List<String>
)
