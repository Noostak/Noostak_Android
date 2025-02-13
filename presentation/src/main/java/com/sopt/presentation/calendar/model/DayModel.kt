package com.sopt.presentation.calendar.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class DayModel(
    val day: LocalDate = LocalDate.now(),
    val isOtherMonth: Boolean = false
)
