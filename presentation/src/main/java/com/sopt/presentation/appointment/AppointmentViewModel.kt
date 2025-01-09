package com.sopt.presentation.appointment

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.AvailableTimeEntity
import com.sopt.domain.entity.MemberAvailableTimeEntity
import com.sopt.domain.entity.PeriodEntity
import com.sopt.domain.entity.PriorityEntity
import com.sopt.domain.entity.RecommendationEntity
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

    val mockAvailablePeriods = PeriodEntity(
        dates = listOf("2024-09-05T10:00:00", "2024-09-06T10:00:00", "2024-09-07T10:00:00"),
        startTime = "2024-09-05T10:00:00",
        endTime = "2024-09-05T18:00:00"
    )

    val mockAvailableTimes = TimeTableEntity(
        members = listOf(
            MemberAvailableTimeEntity(
                memberId = 1,
                memberName = "권장순",
                times = listOf(
                    AvailableTimeEntity(
                        date = "2024-09-05T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-05T11:00:00",
                                memberEndTime = "2024-09-05T12:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-05T13:00:00",
                                memberEndTime = "2024-09-05T14:00:00"
                            )
                        )
                    ),
                    AvailableTimeEntity(
                        date = "2024-09-06T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-06T14:00:00",
                                memberEndTime = "2024-09-06T15:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-06T16:00:00",
                                memberEndTime = "2024-09-06T17:00:00"
                            )
                        )
                    ),
                    AvailableTimeEntity(
                        date = "2024-09-07T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-07T10:00:00",
                                memberEndTime = "2024-09-07T11:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-07T12:00:00",
                                memberEndTime = "2024-09-07T13:00:00"
                            )
                        )
                    )
                )
            ),
            MemberAvailableTimeEntity(
                memberId = 2,
                memberName = "김민수",
                times = listOf(
                    AvailableTimeEntity(
                        date = "2024-09-05T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-05T10:00:00",
                                memberEndTime = "2024-09-05T11:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-05T12:00:00",
                                memberEndTime = "2024-09-05T13:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-05T13:00:00",
                                memberEndTime = "2024-09-05T14:00:00"
                            )
                        )
                    ),
                    AvailableTimeEntity(
                        date = "2024-09-06T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-06T14:00:00",
                                memberEndTime = "2024-09-06T15:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-06T15:00:00",
                                memberEndTime = "2024-09-06T16:00:00"
                            )
                        )
                    ),
                    AvailableTimeEntity(
                        date = "2024-09-07T00:00:00",
                        times = listOf(
                            TimeEntity(
                                memberStartTime = "2024-09-07T11:00:00",
                                memberEndTime = "2024-09-07T12:00:00"
                            ),
                            TimeEntity(
                                memberStartTime = "2024-09-07T12:00:00",
                                memberEndTime = "2024-09-07T13:00:00"
                            )
                        )
                    )
                )
            )
        )
    )

    val mockRecommendations =
        AppointmentEntity(
            isSubmitted = false,
            priorities = listOf(
                PriorityEntity(
                    priority = 1,
                    availableMembersCount = 6,
                    totalMembersCount = 10,
                    recommendations = listOf(
                        RecommendationEntity(
                            id = 1,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = true,
                            availableMembersCount = 5,
                            availableMembers = listOf(
                                "나", "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("한강", "이영희", "박영수", "최영희", "정영수")
                        ),
                        RecommendationEntity(
                            id = 2,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = true,
                            availableMembersCount = 10,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                PriorityEntity(
                    priority = 2,
                    availableMembersCount = 5,
                    totalMembersCount = 10,
                    recommendations = listOf(
                        RecommendationEntity(
                            id = 3,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = false,
                            availableMembersCount = 10,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        ),
                        RecommendationEntity(
                            id = 4,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = true,
                            availableMembersCount = 15,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        ),
                        RecommendationEntity(
                            id = 5,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = false,
                            availableMembersCount = 15,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                PriorityEntity(
                    priority = 3,
                    availableMembersCount = 4,
                    totalMembersCount = 10,
                    recommendations = listOf(
                        RecommendationEntity(
                            id = 6,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = false,
                            availableMembersCount = 15,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                PriorityEntity(
                    priority = 4,
                    availableMembersCount = 3,
                    totalMembersCount = 10,
                    recommendations = listOf(
                        RecommendationEntity(
                            id = 6,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = false,
                            availableMembersCount = 20,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                ),
                PriorityEntity(
                    priority = 5,
                    availableMembersCount = 2,
                    totalMembersCount = 10,
                    recommendations = listOf(
                        RecommendationEntity(
                            id = 7,
                            date = "2024-09-27",
                            startTime = "11:00",
                            endTime = "14:00",
                            likes = 15,
                            isLiked = true,
                            availableMembersCount = 25,
                            availableMembers = listOf(
                                "선우정아", "대한민국만세", "최영희", "정영수",
                                "이가을", "김언지", "박유진", "임하늘", "변우석", "김혜윤", "정해인", "카리나", "닝닝",
                                "지젤", "장원영", "이채연", "김민주", "김채원", "김민주", "김채원", "김민주"
                            ),
                            unavailableMembersCount = 5,
                            unavailableMembers = listOf("나", "한강", "이영희", "박영수", "최영희", "정영수")
                        )
                    )
                )
            )
        )
}
