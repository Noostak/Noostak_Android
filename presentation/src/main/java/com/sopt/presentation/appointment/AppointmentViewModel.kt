package com.sopt.presentation.appointment

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.RecommendationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor() : BaseViewModel<AppointmentSideEffect>() {
    fun navigateUp() {
        emitSideEffect(AppointmentSideEffect.NavigateUp)
    }

    val mockRecommendations = listOf(
        AppointmentEntity(
            priority = 1,
            recommendations = listOf(
                RecommendationEntity(
                    id = 1,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                ),
                RecommendationEntity(
                    id = 2,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝", "지젤"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                ),
                RecommendationEntity(
                    id = 3,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("나", "윈터", "닝닝", "지젤"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("카리나")
                )
            )
        ),
        AppointmentEntity(
            priority = 2,
            recommendations = listOf(
                RecommendationEntity(
                    id = 4,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                ),
                RecommendationEntity(
                    id = 5,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝", "지젤"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                )
            )
        ),
        AppointmentEntity(
            priority = 3,
            recommendations = listOf(
                RecommendationEntity(
                    id = 6,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                )
            )
        ),
        AppointmentEntity(
            priority = 4,
            recommendations = listOf(
                RecommendationEntity(
                    id = 7,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("나", "윈터", "닝닝"),
                    unavailableMembersCount = 8,
                    unavailableMembers = listOf("변우석", "카리나", "지젤", "이가을", "김혜윤", "정해인", "대한민국만세", "유연석")
                )
            )
        ),
        AppointmentEntity(
            priority = 5,
            recommendations = listOf(
                RecommendationEntity(
                    id = 8,
                    date = "2021-09-01",
                    startTime = "09:00",
                    endTime = "10:00",
                    likes = 3,
                    availableMembersCount = 3,
                    availableMembers = listOf("카리나", "윈터", "닝닝"),
                    unavailableMembersCount = 1,
                    unavailableMembers = listOf("나")
                )
            )
        )
    )
}

sealed class AppointmentSideEffect {
    data object NavigateUp : AppointmentSideEffect()
}
