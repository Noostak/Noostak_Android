package com.sopt.presentation.calendar

import com.sopt.core.util.BaseViewModel
import com.sopt.domain.entity.CalendarGroupEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : BaseViewModel<CalendarSideEffect>() {
    val mockGroups = listOf(
        CalendarGroupEntity(
            id = 1,
            groupName = "가응가",
            groupImage = "https://avatars.githubusercontent.com/u/91470334?v=4"
        ),
        CalendarGroupEntity(
            id = 2,
            groupName = "먼지 난다",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 3,
            groupName = "유잔면",
            groupImage = "https://avatars.githubusercontent.com/u/68536115?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 4,
            groupName = "마늘",
            groupImage = "https://avatars.githubusercontent.com/u/79982452?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 5,
            groupName = "누스탁1",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 6,
            groupName = "누스탁2",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 7,
            groupName = "누스탁3",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 8,
            groupName = "누스탁4",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        ),
        CalendarGroupEntity(
            id = 9,
            groupName = "누스탁5",
            groupImage = "https://avatars.githubusercontent.com/u/85453429?s=96&v=4"
        )
    )
}
