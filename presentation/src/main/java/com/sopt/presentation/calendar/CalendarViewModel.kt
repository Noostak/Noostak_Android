package com.sopt.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.CalendarSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : BaseViewModel<CalendarSideEffect>() {

    private val _scheduleMap = MutableStateFlow<Map<String, List<CalendarSchedule>>>(emptyMap())
    val scheduleMap: StateFlow<Map<String, List<CalendarSchedule>>> get() = _scheduleMap

    init {
        loadSampleSchedule()
    }

    private fun loadSampleSchedule() {
        viewModelScope.launch {
            _scheduleMap.value = mapOf(
                "2025-02-04" to listOf(
                    CalendarSchedule(title = "회의", color = "#A9DBBE"),
                    CalendarSchedule(title = "책 읽기", color = "#8D78D8"),
                    CalendarSchedule(title = "영화 관람", color = "#3E8EFF"),
                    CalendarSchedule(title = "스터디 모임", color = "#3E8EFF"),
                    CalendarSchedule(title = "저녁 식사", color = "#3E8EFF")
                ),
                "2025-02-12" to listOf(
                    CalendarSchedule(title = "회의", color = "#A9DBBE"),
                    CalendarSchedule(title = "운동", color = "#8D78D8")
                ),
                "2025-02-13" to listOf(
                    CalendarSchedule(title = "회의", color = "#A9DBBE")
                ),
                "2025-02-14" to listOf(
                    CalendarSchedule(title = "출근", color = "#3E8EFF"),
                    CalendarSchedule(title = "이름이 긴 약속", color = "#8D78D8")
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
                CalendarSchedule(title = "월간 미팅", color = "#FF5733"),
                CalendarSchedule(title = "팀 회의", color = "#33A8FF"),
                CalendarSchedule(title = "운동", color = "#28A745")
            )

            _scheduleMap.value = newScheduleMap
        }
    }
}
