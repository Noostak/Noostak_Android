package com.sopt.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ScheduleEntity(
    val groupId: Long = -1,
    val date: String, // M월 D일 (E) - ex) 1월 13일 (월)
    val scheduleList: List<ScheduleListDetailEntity>
)

@Parcelize
data class ScheduleListDetailEntity(
    val scheduleId: Long = -1,
    val name: String = "",
    val category: String = "",
    val startTime: String = "", // H:m - ex) 1:10, 13:01
    val endTime: String = "", // H:m - ex) 1:10, 13:01
    val duration: Long = 0
) : Parcelable
