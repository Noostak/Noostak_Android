package com.sopt.presentation.appointment.appointmentConfirm

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AppointmentConfirmViewModel @Inject constructor(
    private val appointmentConfirmRepository: AppointmentConfirmRepository
) : BaseViewModel<AppointmentConfirmSideEffect>() {
    private val _showErrorDialog = MutableStateFlow(Pair(false, DialogType.DATA_FAILURE))
    val showErrorDialog: StateFlow<Pair<Boolean, DialogType>> get() = _showErrorDialog.asStateFlow()

    private val _getConfirmedState: MutableStateFlow<UiState<AppointmentDetailEntity>> =
        MutableStateFlow(UiState.Empty)
    val getConfirmedState: StateFlow<UiState<AppointmentDetailEntity>> =
        _getConfirmedState.asStateFlow()

    private val _postConfirmedState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Empty)

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

    fun postOptionConfirm(groupId: Long, appointmentOptionId: Long) {
        viewModelScope.launch {
            _postConfirmedState.emit(UiState.Loading)
            appointmentConfirmRepository.postOptionConfirm(appointmentOptionId).fold(
                onSuccess = {
                    _postConfirmedState.emit(UiState.Success(it))
                    emitSideEffect(AppointmentConfirmSideEffect.NavigateToGroupDetail(groupId))
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is IOException -> { // 네트워크 에러
                            _postConfirmedState.emit(UiState.Failure(throwable.message.toString()))
                            emitSideEffect(
                                AppointmentConfirmSideEffect.ShowErrorDialog(
                                    true,
                                    DialogType.NETWORK_FAILURE
                                )
                            )
                        }

                        else -> { // 서버 통신 에러
                            _postConfirmedState.emit(UiState.Failure(throwable.message.toString()))
                            emitSideEffect(
                                AppointmentConfirmSideEffect.ShowErrorDialog(
                                    true,
                                    DialogType.DATA_FAILURE
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    fun showErrorDialog(show: Boolean, dialogType: DialogType) {
        _showErrorDialog.update { it.copy(first = show, second = dialogType) }
    }

    fun navigateUp() {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateUp)
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateToGroupDetail(groupId))
    }
}

sealed class AppointmentConfirmSideEffect {
    data object NavigateUp : AppointmentConfirmSideEffect()
    data class NavigateToGroupDetail(
        val groupId: Long
    ) : AppointmentConfirmSideEffect()

    data class ShowErrorDialog(val show: Boolean, val dialogType: DialogType) :
        AppointmentConfirmSideEffect()
}
