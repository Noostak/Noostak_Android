package com.sopt.presentation.groupDetail.confirmedDetail

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.ConfirmedDetailEntity
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.repository.GroupDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfirmedDetailViewModel @Inject constructor(
    private val groupDetailRepository: GroupDetailRepository
) : BaseViewModel<ConfirmedDetailSideEffect>() {

    private val _confirmedDetailState = MutableStateFlow<UiState<ConfirmedDetailEntity>>(UiState.Empty)
    val confirmedDetailState: StateFlow<UiState<ConfirmedDetailEntity>> = _confirmedDetailState

    fun getConfirmedDetail(appointmentId: Long) {
        viewModelScope.launch {
            _confirmedDetailState.value = UiState.Loading
            groupDetailRepository.getConfirmedDetail(appointmentId)
                .onSuccess { entity ->
                    _confirmedDetailState.value = UiState.Success(entity)
                }.onFailure {
                    Timber.e(it)
                    _confirmedDetailState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun navigateUp() {
        emitSideEffect(ConfirmedDetailSideEffect.NavigateUp)
    }

    val mockConfirmedDetail = ConfirmedDetailEntity(
        myIdentity = IdentityEntity(
            availability = "available",
            position = 0,
            name = "이가을"
        ),
        date = "2025-01-06T00:00:00",
        startTime = "2025-01-06T11:00:00",
        endTime = "2025-01-06T14:00:00",
        category = "기타",
        availableMembersCount = 5,
        availableMembers = listOf(
            "이가을", "대한민국만세", "최영희", "정영수",
            "선우정아", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
            "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
        ),
        unavailableMembersCount = 5,
        unavailableMembers = listOf(
            "류선재", "한강", "이영희", "박영수", "최영희", "정영수", "김태성", "백인혁", "임솔"
        )
    )
}

sealed class ConfirmedDetailSideEffect {
    data object NavigateUp : ConfirmedDetailSideEffect()
}
