package com.sopt.domain.entity

data class TimeTableEntity(
    val members: List<MemberAvailableTimeEntity>
)

data class MemberAvailableTimeEntity(
    val memberId: Int,
    val memberName: String,
    val times: List<AvailableTimeEntity>
)

data class AvailableTimeEntity(
    val date: String,
    val times: List<TimeEntity>
)

data class TimeEntity(
    val memberStartTime: String,
    val memberEndTime: String
)