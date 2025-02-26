package com.sopt.presentation.appointment.appointmentCheck

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentCheckViewModel @Inject constructor(
    private val appointmentConfirmRepository: AppointmentConfirmRepository
) : BaseViewModel<AppointmentCheckSideEffect>() {
    private val _postTimeTableState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Empty)
    val postTimeTableState: StateFlow<UiState<Unit>> get() = _postTimeTableState.asStateFlow()

    fun postTimeTable(appointmentId: Long, availableTimes: List<TimeEntity>) {
        viewModelScope.launch {
            _postTimeTableState.emit(UiState.Loading)
            appointmentConfirmRepository.postTimeTable(appointmentId, availableTimes).fold(
                onSuccess = {
                    _postTimeTableState.emit(UiState.Success(it))
                },
                onFailure = {
                    _postTimeTableState.emit(UiState.Failure(it.message.toString()))
                }
            )
        }
    }

    fun navigateUp() {
        emitSideEffect(AppointmentCheckSideEffect.NavigateUp)
    }

    fun navigateToAppointment(groupId: Long, appointmentId: Long, appointmentName: String) {
        emitSideEffect(
            AppointmentCheckSideEffect.NavigateToAppointment(
                groupId,
                appointmentId,
                appointmentName
            )
        )
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentCheckSideEffect.NavigateToGroupDetail(groupId))
    }

    val mockAvailablePeriods = listOf(
        TimeEntity(
            date = "2024-09-05T10:00:00",
            startTime = "2024-09-05T10:00:00",
            endTime = "2024-09-05T18:00:00"
        ),
        TimeEntity(
            date = "2024-09-06T10:00:00",
            startTime = "2024-09-06T10:00:00",
            endTime = "2024-09-06T18:00:00"
        ),
        TimeEntity(
            date = "2024-09-07T10:00:00",
            startTime = "2024-09-07T10:00:00",
            endTime = "2024-09-07T18:00:00"
        )
    )
}

sealed class AppointmentCheckSideEffect {
    data object NavigateUp : AppointmentCheckSideEffect()
    data class NavigateToAppointment(
        val groupId: Long,
        val appointmentId: Long,
        val appointmentName: String
    ) : AppointmentCheckSideEffect()

    data class NavigateToGroupDetail(val groupId: Long) : AppointmentCheckSideEffect()
}