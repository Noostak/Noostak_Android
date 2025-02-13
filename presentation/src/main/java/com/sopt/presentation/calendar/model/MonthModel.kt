package com.sopt.presentation.calendar.model

import androidx.compose.runtime.Immutable
import com.sopt.domain.entity.DayEntity
import java.time.YearMonth


@Immutable
data class MonthModel(
    val yearMonth: YearMonth
) {
    private val startDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    val prevMonthDays = startDayOfWeek
    val monthDays = yearMonth.lengthOfMonth()
    val nextMonthDays = (7 - ((prevMonthDays + monthDays) % 7)) % 7

    val calendarMonth: List<List<DayEntity>> =
        (0 until prevMonthDays + monthDays + nextMonthDays)
            .chunked(7) { it.map(::getDay) }

    private fun getDay(offset: Int): DayEntity {
        val date = yearMonth.atDay(1).minusDays(prevMonthDays.toLong()).plusDays(offset.toLong())
        return DayEntity(day = date, isOtherMonth = YearMonth.from(date) != yearMonth)
    }

    override fun toString(): String = "%d년 %d월".format(yearMonth.year, yearMonth.monthValue)
}
