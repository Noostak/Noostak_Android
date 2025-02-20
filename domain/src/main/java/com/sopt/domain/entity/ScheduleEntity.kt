package com.sopt.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ScheduleEntity(
    val date: String,
    val scheduleList: List<ScheduleDetailEntity>
)

@Parcelize
data class ScheduleDetailEntity(
    val id: Long,
    val name: String,
    val category: String,
    val time: String,
    val duration: String,
    val availableMembers: List<String>,
    val unavailableMembers: List<String>
) : Parcelable
