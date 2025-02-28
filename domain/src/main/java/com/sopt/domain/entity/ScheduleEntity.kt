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
    val startTime: String,
    val endTime: String,
    val duration: Int
) : Parcelable
