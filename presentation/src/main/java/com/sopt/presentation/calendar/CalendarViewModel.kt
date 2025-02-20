package com.sopt.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.CalendarSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : BaseViewModel<CalendarSideEffect>() {
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog get() = _showAddDialog

    private val _scheduleMap = MutableStateFlow<Map<String, List<CalendarSchedule>>>(emptyMap())
    val scheduleMap: StateFlow<Map<String, List<CalendarSchedule>>> get() = _scheduleMap

    init {
        loadSampleSchedule()
    }

    fun showAddDialog(show: Boolean) {
        _showAddDialog.update { show }
    }

    fun navigateToGroupCreate() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupCreate)
    }

    fun navigateToGroupEnter() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupEnter)
    }

    private fun loadSampleSchedule() {
        viewModelScope.launch {
            _scheduleMap.value = mapOf(
                "2025-02-04" to listOf(
                    CalendarSchedule(title = "회의", categoryType = "중요"),
                    CalendarSchedule(title = "책 읽기", categoryType = "취미"),
                    CalendarSchedule(title = "영화 관람", categoryType = "일정"),
                    CalendarSchedule(title = "스터디 모임", categoryType = "중요"),
                    CalendarSchedule(title = "저녁 식사", categoryType = "일정")
                ),
                "2025-02-12" to listOf(
                    CalendarSchedule(title = "회의", categoryType = "중요"),
                    CalendarSchedule(title = "운동", categoryType = "취미")
                ),
                "2025-02-13" to listOf(
                    CalendarSchedule(title = "회의", categoryType = "중요")
                ),
                "2025-02-14" to listOf(
                    CalendarSchedule(title = "출근", categoryType = "일정"),
                    CalendarSchedule(title = "이름이 긴 약속", categoryType = "기타")
                )
            )
        }
    }

    // 특정 월의 데이터 불러오기
    fun getScheduleMonth(date: LocalDate) {
        val monthKey = date.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        viewModelScope.launch {
            val newScheduleMap = _scheduleMap.value.toMutableMap()

            newScheduleMap[monthKey] = listOf(
                CalendarSchedule(title = "월간 미팅", categoryType = "중요"),
                CalendarSchedule(title = "팀 회의", categoryType = "중요"),
                CalendarSchedule(title = "운동", categoryType = "취미")
            )

            _scheduleMap.value = newScheduleMap
        }
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
}
