package com.sopt.presentation.appointment.appointmentConfirm

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.repository.AppointmentConfirmRepository
import com.sopt.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentConfirmViewModel @Inject constructor(
    private val appointmentConfirmRepository: AppointmentConfirmRepository
) : BaseViewModel<AppointmentConfirmSideEffect>() {
    private val _getConfirmedState: MutableStateFlow<UiState<AppointmentDetailEntity>> =
        MutableStateFlow(UiState.Empty)
    val getConfirmedState: MutableStateFlow<UiState<AppointmentDetailEntity>> = _getConfirmedState

    private val _postConfirmedState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Empty)
    val postConfirmedState: MutableStateFlow<UiState<Unit>> = _postConfirmedState

    fun getConfirmed(appointmentOptionId: Long) {
        viewModelScope.launch {
            _getConfirmedState.emit(UiState.Loading)
            appointmentConfirmRepository.getConfirmed(appointmentOptionId).let { result ->
                result.onSuccess {
                    _getConfirmedState.emit(UiState.Success(it))
                }.onFailure {
                    _getConfirmedState.emit(UiState.Failure(it.message.toString()))
                }
            }
        }
    }

    fun postConfirmed(appointmentOptionId: Long) {
        viewModelScope.launch {
            _postConfirmedState.emit(UiState.Loading)
            appointmentConfirmRepository.postConfirmed(appointmentOptionId).let { result ->
                result.onSuccess {
                    _postConfirmedState.emit(UiState.Success(it))
                }.onFailure {
                    _postConfirmedState.emit(UiState.Failure(it.message.toString()))
                    emitSideEffect(AppointmentConfirmSideEffect.ShowToast(R.string.appointment_confirm_failure))
                }
            }
        }
    }

    fun navigateUp() {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateUp)
    }

    fun navigateToGroupDetail(groupId: Long) {
        emitSideEffect(AppointmentConfirmSideEffect.NavigateToGroupDetail(groupId))
    }

    val mockAppointmentDetail = AppointmentDetailEntity(
        isHost = true,
        myIdentity = IdentityEntity(
            availability = "unavailable",
            position = 2,
            name = "박영수"
        ),
        date = "2025-01-06T00:00:00",
        startTime = "2025-01-06T11:00:00",
        endTime = "2025-01-06T14:00:00",
        category = "기타",
        availableMembersCount = 22,
        availableMembers = listOf(
            "선우정아", "대한민국만세", "최영희", "정영수",
            "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
    )
}

sealed class AppointmentConfirmSideEffect {
    data object NavigateUp : AppointmentConfirmSideEffect()
    data class NavigateToGroupDetail(
        val groupId: Long
    ) : AppointmentConfirmSideEffect()

    data class ShowToast(val message: Int) : AppointmentConfirmSideEffect()
}
