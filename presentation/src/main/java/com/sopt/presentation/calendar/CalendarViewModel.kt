package com.sopt.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.sopt.core.extension.toDateString
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.CalendarAppointmentDayEntity
import com.sopt.domain.entity.CalendarAppointmentEntity
import com.sopt.domain.entity.CalendarGroupEntity
import com.sopt.domain.entity.CalendarSchedule
import com.sopt.domain.repository.AppointmentConfirmRepository
import com.sopt.domain.usecase.GetCalendarAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarAppointmentsUseCase: GetCalendarAppointmentsUseCase,
    private val appointmentConfirmRepository: AppointmentConfirmRepository
) :
    BaseViewModel<CalendarSideEffect>() {
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog get() = _showAddDialog

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> get() = _showBottomSheet

    private val _scheduleMap = MutableStateFlow<Map<String, List<CalendarSchedule>>>(emptyMap())
    val scheduleMap: StateFlow<Map<String, List<CalendarSchedule>>> get() = _scheduleMap

    private var currentYearMonth: YearMonth = YearMonth.now()

    private val _selectedDayAppointments =
        MutableStateFlow<List<CalendarAppointmentEntity>>(emptyList())
    val selectedDayAppointments: StateFlow<List<CalendarAppointmentEntity>> get() = _selectedDayAppointments

    private var _currentMonthAppointments = emptyList<CalendarAppointmentDayEntity>()

    private val _getConfirmedState: MutableStateFlow<UiState<AppointmentDetailEntity>> =
        MutableStateFlow(UiState.Empty)
    val getConfirmedState: StateFlow<UiState<AppointmentDetailEntity>> =
        _getConfirmedState.asStateFlow()

    init {
        getCalendarAppointments(currentYearMonth.year, currentYearMonth.monthValue)
    }

    fun getOptionDetail(appointmentOptionId: Long) {
        viewModelScope.launch {
            _getConfirmedState.emit(UiState.Loading)
            appointmentConfirmRepository.getOptionDetail(appointmentOptionId).fold(
                onSuccess = {
                    _getConfirmedState.emit(UiState.Success(it))
                },
                onFailure = {
                    _getConfirmedState.emit(UiState.Failure(it.message.toString()))
                }
            )
        }
    }

    fun showAddDialog(show: Boolean) {
        _showAddDialog.update { show }
    }

    fun showBottomSheet(show: Boolean) {
        _showBottomSheet.update { show }
    }

    private fun triggerShowBottomSheet() {
        emitSideEffect(CalendarSideEffect.ShowBottomSheet)
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
            getCalendarAppointmentsUseCase(10006, year, month)
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
                        _currentMonthAppointments = response.currentMonthAppointments
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

    // 날짜 클릭
    fun onDayClicked(date: LocalDate) {
        val appointments = _currentMonthAppointments
            .firstOrNull {
                it.day == date.dayOfMonth &&
                        currentYearMonth.year == date.year &&
                        currentYearMonth.monthValue == date.monthValue
            }?.appointments ?: emptyList()

        _selectedDayAppointments.value = appointments
        triggerShowBottomSheet()
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
