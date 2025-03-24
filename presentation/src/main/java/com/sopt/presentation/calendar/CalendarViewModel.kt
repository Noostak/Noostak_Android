package com.sopt.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.sopt.core.extension.toDateString
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.entity.ScheduleDetailEntity
import com.sopt.domain.entity.ScheduleEntity
import com.sopt.domain.entity.ScheduleListDetailEntity
import com.sopt.domain.usecase.GetCalendarAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarAppointmentsUseCase: GetCalendarAppointmentsUseCase
) :
    BaseViewModel<CalendarSideEffect>() {
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog get() = _showAddDialog

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> get() = _showBottomSheet

    private val _scheduleMap = MutableStateFlow<Map<String, List<CalendarSchedule>>>(emptyMap())
    val scheduleMap: StateFlow<Map<String, List<CalendarSchedule>>> get() = _scheduleMap

    private var currentYearMonth: YearMonth = YearMonth.now()

    init {
        getCalendarAppointments(currentYearMonth.year, currentYearMonth.monthValue)
    }

    fun showAddDialog(show: Boolean) {
        _showAddDialog.update { show }
    }

    fun showBottomSheet(show: Boolean) {
        _showBottomSheet.update { show }
    }

    fun navigateToGroupCreate() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupCreate)
    }

    fun navigateToGroupEnter() {
        emitSideEffect(CalendarSideEffect.NavigateToGroupEnter)
    }

    fun navigateToAppointmentCreate() {
        emitSideEffect(CalendarSideEffect.NavigateToAppointmentCreate)
    }

    // 캘린더 약속 정보 가져오기
    private fun getCalendarAppointments(year: Int, month: Int) {
        viewModelScope.launch {
            getCalendarAppointmentsUseCase(1, year, month)
                .fold(
                    onSuccess = { response ->
                        val newScheduleMap =
                            response.currentMonthAppointments.associate { dayAppointments ->
                                LocalDate.of(year, month, dayAppointments.day)
                                    .toDateString() to dayAppointments.appointments.map { appointment ->
                                    CalendarSchedule(
                                        scrapId = appointment.id,
                                        title = appointment.name,
                                        categoryType = appointment.category
                                    )
                                }
                            }
                        _scheduleMap.value = newScheduleMap
                    },
                    onFailure = { error ->
                        Timber.e("getCalendarAppointments Failed: ${error.message}")
                    }
                )
        }
    }

    // 현재 월 변경 시 캘린더 API 재호출
    fun onMonthChanged(newYearMonth: YearMonth) {
        if (currentYearMonth != newYearMonth) {
            currentYearMonth = newYearMonth
            getCalendarAppointments(newYearMonth.year, newYearMonth.monthValue)
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

    val mockScheduleList = ScheduleEntity(
        groupId = 1,
        date = "1월 13일 (월)",
        scheduleList = listOf(
            ScheduleListDetailEntity(
                scheduleId = 1,
                name = "누스탁 회의",
                category = "중요",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 5
            ),
            ScheduleListDetailEntity(
                scheduleId = 2,
                name = "누스탁 모각작",
                category = "일정",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 10
            ),
            ScheduleListDetailEntity(
                scheduleId = 3,
                name = "누스탁 회식",
                category = "취미",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 6
            ),
            ScheduleListDetailEntity(
                scheduleId = 4,
                name = "누스탁 MT",
                category = "기타",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 9
            ),
            ScheduleListDetailEntity(
                scheduleId = 5,
                name = "누스탁 회의2",
                category = "중요",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 24
            ),
            ScheduleListDetailEntity(
                scheduleId = 6,
                name = "누스탁 모각작2",
                category = "일정",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 1
            ),
            ScheduleListDetailEntity(
                scheduleId = 7,
                name = "누스탁 회식2",
                category = "취미",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 4
            ),
            ScheduleListDetailEntity(
                scheduleId = 8,
                name = "누스탁 MT2",
                category = "기타",
                startTime = "1/13 21:00",
                endTime = "1/13 21:00",
                duration = 3
            )
        )
    )

    val mockScheduleDetail = ScheduleDetailEntity(
        myIdentity = IdentityEntity(
            availability = "available",
            position = 0,
            name = "김언지"
        ),
        appointmentName = "누스탁이올시다 으아아아아아아아아아아아아아",
        date = "1월 13일 (월)",
        startTime = "1/13 21:00",
        endTime = "1/13 21:00",
        category = "기타",
        availableMembersCount = 81,
        availableMembers = listOf(
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이",
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이",
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이",
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이",
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이",
            "하루", "야마다", "이누마키", "츠키시마",
            "마이키", "호크스", "토도로키", "아이자와", "리바이", "이구로", "호시나", "신에이"
        ),
        unavailableMembersCount = 3,
        unavailableMembers = listOf("박보검", "정해인", "권지용")
    )
}
