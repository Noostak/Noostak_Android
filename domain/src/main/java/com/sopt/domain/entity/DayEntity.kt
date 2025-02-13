package com.sopt.domain.entity

import java.time.LocalDate

data class DayModel(
    val day: LocalDate = LocalDate.now(),
    val isOtherMonth: Boolean = false
)
