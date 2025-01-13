package com.sopt.presentation.appointment

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.AvailableTimeEntity
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.entity.OptionEntity
import com.sopt.domain.entity.RecommendationPriorityEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor() : BaseViewModel<AppointmentSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentSideEffect.NavigateUp)
    }

    fun navigateToAppointmentCheck(groupId: Long, appointmentsId: Long, appointmentName: String) {
        emitSideEffect(
            AppointmentSideEffect.NavigateToAppointmentCheck(
                groupId,
                appointmentsId,
                appointmentName
            )
        )
    }

    fun navigateToAppointmentConfirm(
        groupId: Long,
        appointmentsId: Long,
        optionId: Long,
        appointmentName: String
    ) {
        emitSideEffect(
            AppointmentSideEffect.NavigateToAppointmentConfirm(
                groupId,
                appointmentsId,
                optionId,
                appointmentName
            )
        )
    }

    val mockCurrentStatus =
        TimeTableEntity(
            startTime = "07:00",
            endTime = "23:00",
            timeEntity = listOf(
                TimeEntity(
                    date = "2024-09-27",
                    times = null
                ),
                TimeEntity(
                    date = "2024-09-28",
                    times = listOf(
                        AvailableTimeEntity(
                            startTime = "11:00",
                            endTime = "12:00",
                            level = 10
                        ),
                        AvailableTimeEntity(
                            startTime = "12:00",
                            endTime = "13:00",
                            level = 30
                        ),
                        AvailableTimeEntity(
                            startTime = "13:00",
                            endTime = "14:00",
                            level = 70
                        ),
                        AvailableTimeEntity(
                            startTime = "14:00",
                            endTime = "15:00",
                            level = 90
                        )
                    )
                ),
                TimeEntity(
                    date = "2024-09-29",
                    times = null
                ),
                TimeEntity(
                    date = "2024-09-30",
                    times = null
                ),
                TimeEntity(
                    date = "2024-10-01",
                    times = listOf(
                        AvailableTimeEntity(
                            startTime = "11:00",
                            endTime = "12:00",
                            level = 10
                        ),
                        AvailableTimeEntity(
                            startTime = "12:00",
                            endTime = "13:00",
                            level = 60
                        ),
                        AvailableTimeEntity(
                            startTime = "13:00",
                            endTime = "14:00",
                            level = 80
                        ),
                        AvailableTimeEntity(
                            startTime = "14:00",
                            endTime = "15:00",
                            level = 100
                        )
                    )
                ),
                TimeEntity(
                    date = "2024-10-02",
                    times = listOf(
                        AvailableTimeEntity(
                            startTime = "11:00",
                            endTime = "12:00",
                            level = 10
                        ),
                        AvailableTimeEntity(
                            startTime = "12:00",
                            endTime = "13:00",
                            level = 70
                        ),
                        AvailableTimeEntity(
                            startTime = "13:00",
                            endTime = "14:00",
                            level = 90
                        ),
                        AvailableTimeEntity(
                            startTime = "14:00",
                            endTime = "15:00",
                            level = 100
                        )
                    )
                ),
                TimeEntity(
                    date = "2024-10-03",
                    times = null
                )
            )
        )

    val mockRecommendations =
        AppointmentEntity(
            isSubmitted = true,
            isHost = true,
            recommendationPriority = listOf(
                RecommendationPriorityEntity(
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
