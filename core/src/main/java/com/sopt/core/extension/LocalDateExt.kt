package com.sopt.core.extension

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

fun YearMonth.toYearMonthString(): String = "${year}년 ${monthValue}월"

fun LocalDate.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return format(formatter)
}

fun LocalDate.isToday(): Boolean = this == LocalDate.now()
