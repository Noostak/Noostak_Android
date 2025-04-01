package com.sopt.presentation.appointmentCreate.appointmentSubmit

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.repository.AppointmentCreateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AppointmentSubmitViewModel @Inject constructor(
    private val appointmentCreateRepository: AppointmentCreateRepository
) : BaseViewModel<AppointmentSubmitSideEffect>() {

    private val _showErrorDialog = MutableStateFlow(Pair(false, DialogType.DATA_FAILURE))
    val showErrorDialog: StateFlow<Pair<Boolean, DialogType>> get() = _showErrorDialog

    private val _postAppointmentCreateState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Empty)

    fun navigateUp() {
        emitSideEffect(AppointmentSubmitSideEffect.NavigateUp)
    }

    fun postAppointmentCreate(
        groupId: Long,
        appointmentName: String,
        appointmentCategory: String,
        appointmentDuration: Int,
        appointmentDate: List<String>,
        appointmentTime: String?
    ) {
        viewModelScope.launch {
            val inputDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

            val (startTimeStr, endTimeStr) = appointmentTime
                ?.split(" ~ ")
                ?.map { it + ":00" } ?: listOf("00:00:00", "00:00:00")

            val timeList = appointmentDate.map { date ->
                val startDateTime = LocalDateTime.parse("$date $startTimeStr", inputDateFormatter)
                val endDateTime = LocalDateTime.parse("$date $endTimeStr", inputDateFormatter)
                val fullDate = LocalDateTime.parse("$date 00:00:00", inputDateFormatter)

                TimeEntity(
                    date = fullDate.format(outputFormatter),
                    startTime = startDateTime.format(outputFormatter),
                    endTime = endDateTime.format(outputFormatter)
                )
            }

            _postAppointmentCreateState.emit(UiState.Loading)
            appointmentCreateRepository.postAppointmentCreate(
                groupId = groupId,
                appointmentName = appointmentName,
                category = appointmentCategory,
                duration = appointmentDuration * 60,
                appointmentHostSelectionTimes = timeList
            ).fold(
                onSuccess = {
                    _postAppointmentCreateState.emit(UiState.Success(it))

                    emitSideEffect(
                        AppointmentSubmitSideEffect.NavigateToAppointmentSubmitConfirm(
                            groupId = groupId,
                            appointmentName = appointmentName,
                            isConsecutive = appointmentDate.size > 1,
                            appointmentDate = appointmentDate,
                            appointmentTime = appointmentTime,
                            appointmentCategory = appointmentCategory,
                            appointmentDuration = appointmentDuration
                        )
                    )
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is IOException -> {
                            _postAppointmentCreateState.emit(UiState.Failure(throwable.message.toString()))
                            emitSideEffect(AppointmentSubmitSideEffect.ShowErrorDialog(true, DialogType.NETWORK_FAILURE))
                        }
                        else -> {
                            _postAppointmentCreateState.emit(UiState.Failure(throwable.message.toString()))
                            emitSideEffect(AppointmentSubmitSideEffect.ShowErrorDialog(true, DialogType.DATA_FAILURE))
                        }
                    }
                }
            )
        }
    }
    fun showErrorDialog(show: Boolean, dialogType: DialogType) {
        _showErrorDialog.update { it.copy(first = show, second = dialogType) }
    }
}

sealed class AppointmentSubmitSideEffect {
    data object NavigateUp : AppointmentSubmitSideEffect()
    data class NavigateToAppointmentSubmitConfirm(
        val groupId: Long,
        val appointmentName: String,
        val isConsecutive: Boolean,
        val appointmentDate: List<String>,
        val appointmentTime: String?,
        val appointmentCategory: String,
        val appointmentDuration: Int
    ) : AppointmentSubmitSideEffect()
    data class ShowErrorDialog(val show: Boolean, val dialogType: DialogType) : AppointmentSubmitSideEffect()
}
