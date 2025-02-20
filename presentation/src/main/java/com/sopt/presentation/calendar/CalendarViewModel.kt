package com.sopt.presentation.calendar

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.ScheduleDetailEntity
import com.sopt.domain.entity.ScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : BaseViewModel<CalendarSideEffect>() {
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog get() = _showAddDialog

    private val _showSheet = MutableStateFlow(false)
    val showSheet: StateFlow<Boolean> get() = _showSheet

    private val _detailSchedule: MutableStateFlow<ScheduleDetailEntity?> = MutableStateFlow(null)
    val detailSchedule: StateFlow<ScheduleDetailEntity?> get() = _detailSchedule.asStateFlow()

    fun showAddDialog(show: Boolean) {
        _showAddDialog.update { show }
    }

    fun showBottomSheet(show: Boolean) {
        _showSheet.update { show }
    }

    fun updateDetailSchedule(schedule: ScheduleDetailEntity) {
        _detailSchedule.value = schedule
    }

    fun navigateToGroupCreate() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupCreate)
    }

    fun navigateToGroupEnter() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupEnter)
    }

    val mockGroups = listOf(
        CalendarGroupEntity(
            id = 1,
            groupName = "가응가",
            groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
        ),
        CalendarGroupEntity(
            id = 2,
            groupName = "먼지 난다",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 3,
            groupName = "유잔면",
            groupImage = "https://avatars.githubusercontent.com/u/68536115?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 4,
            groupName = "마늘",
            groupImage = "https://avatars.githubusercontent.com/u/79982452?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 5,
            groupName = "누스탁1",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 6,
            groupName = "누스탁2",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 7,
            groupName = "누스탁3",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 8,
            groupName = "누스탁4",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 9,
            groupName = "누스탁5",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        )
    )

    val mockSchedule = ScheduleEntity(
        date = "1월 13일 (월)",
        scheduleList = listOf(
            ScheduleDetailEntity(
                id = 1,
                name = "누스탁 회의",
                category = "중요",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 2,
                name = "누스탁 모각작",
                category = "일정",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 3,
                name = "누스탁 회식",
                category = "취미",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 4,
                name = "누스탁 MT",
                category = "기타",
                time = "1/13 21:00",
                duration = "21:00~23:00",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 5,
                name = "누스탁 회의2",
                category = "중요",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 6,
                name = "누스탁 모각작2",
                category = "일정",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 7,
                name = "누스탁 회식2",
                category = "취미",
                time = "1/13 21:00",
                duration = "하루종일",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            ),
            ScheduleDetailEntity(
                id = 8,
                name = "누스탁 MT2",
                category = "기타",
                time = "1/13 21:00",
                duration = "21:00~23:00",
                availableMembers = listOf(
                    "하루", "야마다", "이누마키", "츠키시마",
                    "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
                ),
                unavailableMembers = listOf("박보검", "정해인", "권지용")
            )
        )
    )
}
