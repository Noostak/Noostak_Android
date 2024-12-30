package com.sopt.presentation.groupDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.domain.entity.ConfirmedEntity
import com.sopt.domain.entity.GroupDetailEntity
import com.sopt.domain.entity.ProgressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor() : ViewModel() {
    private val _sideEffects: MutableSharedFlow<GroupDetailSideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<GroupDetailSideEffect> get() = _sideEffects.asSharedFlow()

    fun navigateUp() {
        viewModelScope.launch {
            _sideEffects.emit(GroupDetailSideEffect.NavigateUp)
        }
    }

    fun navigateToConfirmedDetail(groupId: Long, confirmedId: Long) {
        viewModelScope.launch {
            _sideEffects.emit(GroupDetailSideEffect.NavigateToConfirmedDetail(groupId, confirmedId))
        }
    }

    val tabs = immutableListOf("진행중", "완료")
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
                participants = 0,
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
