package com.sopt.presentation.groupDetail

import android.content.Context
import com.sopt.core.extension.stringOf
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ConfirmedEntity
import com.sopt.domain.entity.GroupDetailEntity
import com.sopt.domain.entity.ProgressEntity
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<GroupDetailSideEffect>() {

    fun navigateUp() {
        emitSideEffect(GroupDetailSideEffect.NavigateUp)
    }

    fun navigateToConfirmedDetail(groupId: Long, confirmedId: Long) {
        emitSideEffect(GroupDetailSideEffect.NavigateToConfirmedDetail(groupId, confirmedId))
    }

    fun navigateToGroupMember(groupId: Long) {
        emitSideEffect(GroupDetailSideEffect.NavigateToGroupMember(groupId))
    }

    fun navigateToAppointment(groupId: Long, appointmentsId: Long, appointmentName: String) {
        emitSideEffect(
            GroupDetailSideEffect.NavigateToAppointment(
                groupId,
                appointmentsId,
                appointmentName
            )
        )
    }

    val tabs = immutableListOf(
        context.stringOf(R.string.tab_group_detail_progress),
        context.stringOf(R.string.tab_group_detail_confirmed)
    )
    val mockGroupDetail = GroupDetailEntity(
        groupName = "누스탁",
        groupMembersCount = 10,
        progressEntities = listOf(
            ProgressEntity(
                appointmentId = 1,
                appointmentName = "1주차",
                date = "2024-09-27",
                weekday = "금",
                startTime = "11:00",
                endTime = "14:00",
                participants = 3,
                maxParticipants = 5
            ),
            ProgressEntity(
                appointmentId = 2,
                appointmentName = "2주차",
                date = "2024-09-27",
                weekday = "금",
                startTime = "11:00",
                endTime = "14:00",
                participants = 2,
                maxParticipants = 5
            ),
            ProgressEntity(
                appointmentId = 3,
                appointmentName = "3주차",
                date = "2024-09-27",
                weekday = "금",
                startTime = "11:00",
                endTime = "14:00",
                participants = 5,
                maxParticipants = 5
            ),
            ProgressEntity(
                appointmentId = 4,
                appointmentName = "4주차",
                date = "2024-09-27",
                weekday = "금",
                startTime = "11:00",
                endTime = "14:00",
                participants = 0,
                maxParticipants = 5
            )
        ),
        confirmedEntities = listOf(
            ConfirmedEntity(
                appointmentId = 1,
                appointmentName = "3차 회의",
                date = "2024-09-27",
                weekday = "금요일"
            ),
            ConfirmedEntity(
                appointmentId = 2,
                appointmentName = "회의",
                date = "2024-09-27",
                weekday = "금요일"
            )
        )
    )
}
