package com.sopt.presentation.appointment

import androidx.lifecycle.viewModelScope
import com.sopt.core.state.UiState
import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.AppointmentMembersInfoEntity
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.entity.OptionEntity
import com.sopt.domain.entity.RecommendationPriorityEntity
import com.sopt.domain.entity.TimeEntity
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

    val mockAvailableTimes = listOf(
        AppointmentMembersInfoEntity(
            memberId = 1,
            memberName = "범태하",
            appointmentMemberAvailableTimes = listOf(
                TimeEntity(
                    date = "2024-09-05T00:00:00",
                    startTime = "2024-09-05T10:00:00",
                    endTime = "2024-09-05T11:00:00"
                ),
                TimeEntity(
                    date = "2024-09-05T00:00:00",
                    startTime = "2024-09-06T14:00:00",
                    endTime = "2024-09-06T15:00:00"
                ),
                TimeEntity(
                    date = "2024-09-06T00:00:00",
                    startTime = "2024-09-06T10:00:00",
                    endTime = "2024-09-06T11:00:00"
                ),
                TimeEntity(
                    date = "2024-09-07T00:00:00",
                    startTime = "2024-09-07T10:00:00",
                    endTime = "2024-09-07T11:00:00"
                )
            )
        ),
        AppointmentMembersInfoEntity(
            memberId = 2,
            memberName = "김민수",
            appointmentMemberAvailableTimes = listOf(
                TimeEntity(
                    date = "2024-09-05T00:00:00",
                    startTime = "2024-09-05T11:00:00",
                    endTime = "2024-09-05T12:00:00"
                ),
                TimeEntity(
                    date = "2024-09-06T00:00:00",
                    startTime = "2024-09-06T11:00:00",
                    endTime = "2024-09-06T12:00:00"
                ),
                TimeEntity(
                    date = "2024-09-07T00:00:00",
                    startTime = "2024-09-07T11:00:00",
                    endTime = "2024-09-07T12:00:00"
                )
            )
        )
    )

    val mockRecommendations =
        AppointmentEntity(
            isHost = true,
            recommendationPriority = listOf(
                RecommendationPriorityEntity(
                    priority = 1,
                    options = listOf(
                        OptionEntity(
                            id = 1,
                            totalMemberCount = 20,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = true,
                            availableMemberCount = 10,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        ),
                        OptionEntity(
                            id = 2,
                            totalMemberCount = 20,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = false,
                            availableMemberCount = 10,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                RecommendationPriorityEntity(
                    priority = 2,
                    options = listOf(
                        OptionEntity(
                            id = 3,
                            totalMemberCount = 10,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = true,
                            availableMemberCount = 5,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        ),
                        OptionEntity(
                            id = 4,
                            totalMemberCount = 10,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = false,
                            availableMemberCount = 5,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                RecommendationPriorityEntity(
                    priority = 3,
                    options = listOf(
                        OptionEntity(
                            id = 5,
                            totalMemberCount = 10,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = true,
                            availableMemberCount = 5,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                RecommendationPriorityEntity(
                    priority = 4,
                    options = listOf(
                        OptionEntity(
                            id = 6,
                            totalMemberCount = 10,
                            myIdentity = IdentityEntity(
                                availability = "available",
                                position = 0,
                                name = "이가을"
                            ),
                            date = "2024-09-27T00:00:00",
                            startTime = "2024-09-27T11:00:00",
                            endTime = "2024-09-27T14:00:00",
                            likes = 15,
                            liked = true,
                            availableMemberCount = 5,
                            availableMembers = listOf(
                                "이가을", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMemberCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                )
            )
        )
}
