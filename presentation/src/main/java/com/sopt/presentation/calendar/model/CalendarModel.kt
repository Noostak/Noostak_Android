package com.sopt.presentation.calendar.model

import java.time.LocalDate
import java.time.YearMonth

class CalendarModel(
    private val startYear: Int = 2020,
    private val endYear: Int = 2030
) {
    private val monthsInRange = (startYear..endYear).flatMap { year ->
        (1..12).map { month -> YearMonth.of(year, month) }
    }

    val initialPage: Int = monthsInRange.indexOf(YearMonth.now())
    val pageCount: Int = monthsInRange.size

    fun getMonthModelByPage(page: Int): MonthModel {
        val yearMonth = monthsInRange[page]
        return MonthModel(yearMonth)
    }

    fun getLocalDateByPage(page: Int): LocalDate {
        return monthsInRange[page].atDay(1)
    }
}
