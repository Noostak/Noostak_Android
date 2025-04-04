package com.sopt.presentation.appointment

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.TimeTableEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val appointmentConfirmRepository: AppointmentConfirmRepository
) : BaseViewModel<AppointmentSideEffect>() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog

    private val _getOptionsState: MutableStateFlow<UiState<AppointmentEntity>> =
        MutableStateFlow(UiState.Empty)
    val getOptionsState: StateFlow<UiState<AppointmentEntity>> get() = _getOptionsState.asStateFlow()

    private val _postLikeState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Empty)
    private val _deleteLikeState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Empty)

    private val _getTimeTableState: MutableStateFlow<UiState<TimeTableEntity>> =
        MutableStateFlow(UiState.Empty)
    val getTimeTableState: StateFlow<UiState<TimeTableEntity>> get() = _getTimeTableState.asStateFlow()

    fun getOptions(appointmentId: Long) {
        viewModelScope.launch {
            _getOptionsState.emit(UiState.Loading)
            appointmentConfirmRepository.getOptions(appointmentId).fold(
                onSuccess = {
                    _getOptionsState.emit(UiState.Success(it))
                },
                onFailure = {
                    _getOptionsState.emit(UiState.Failure(it.message.toString()))
                }
            )
        }
    }

    fun postLike(appointmentId: Long, appointmentOptionId: Long) {
        viewModelScope.launch {
            _postLikeState.emit(UiState.Loading)
            appointmentConfirmRepository.postLike(appointmentId, appointmentOptionId).fold(
                onSuccess = {
                    _postLikeState.emit(UiState.Success(it))
                    Timber.d("postLike success: $it")
                },
                onFailure = {
                    _postLikeState.emit(UiState.Failure(it.message.toString()))
                    Timber.e("postLike failed: ${it.message}")
                }
            )
        }
    }

    fun deleteLike(appointmentId: Long, appointmentOptionId: Long) {
        viewModelScope.launch {
            _deleteLikeState.emit(UiState.Loading)
            appointmentConfirmRepository.deleteLike(appointmentId, appointmentOptionId).fold(
                onSuccess = {
                    _deleteLikeState.emit(UiState.Success(it))
                    Timber.d("deleteLike success: $it")
                },
                onFailure = {
                    _deleteLikeState.emit(UiState.Failure(it.message.toString()))
                    Timber.e("deleteLike failed: ${it.message}")
                }
            )
        }
    }

    fun getTimeTable(appointmentId: Long) {
        viewModelScope.launch {
            _getTimeTableState.emit(UiState.Loading)
            appointmentConfirmRepository.getTimeTable(appointmentId).fold(
                onSuccess = {
                    _getTimeTableState.emit(UiState.Success(it))
                    if (!it.isAppointMemberTimeSet) {
                        emitSideEffect(AppointmentSideEffect.ShowDialog(true))
                    }
                    Timber.d("getTimeTable success: $it")
                },
                onFailure = {
                    _getTimeTableState.emit(UiState.Failure(it.message.toString()))
                    Timber.e("getTimeTable failed: ${it.message}")
                }
            )
        }
    }

    fun navigateUp() {
        emitSideEffect(AppointmentSideEffect.NavigateUp)
    }

    fun navigateToAppointmentConfirm(
        groupId: Long,
        appointmentId: Long,
        optionId: Long,
        appointmentName: String,
        isHost: Boolean
    ) {
        emitSideEffect(
            AppointmentSideEffect.NavigateToAppointmentConfirm(
                groupId,
                appointmentId,
                optionId,
                appointmentName,
                isHost
            )
        )
    }

    fun showDialog(show: Boolean) {
        _showDialog.update { show }
    }
}
