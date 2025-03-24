package com.sopt.core.extension

import com.sopt.domain.entity.DayEntity
import java.time.YearMonth

private val monthsInRange =
    (2020..2030).flatMap { year -> (1..12).map { month -> YearMonth.of(year, month) } }

val initialPage: Int = monthsInRange.indexOf(YearMonth.now())
val pageCount: Int = monthsInRange.size

fun getYearMonthByPage(page: Int): YearMonth {
    return monthsInRange[page]
}

fun getMonthDays(yearMonth: YearMonth): List<List<DayEntity>> {
    val startDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7 // 이번 달 1일의 요일 (일요일 기준)

    val monthDays = yearMonth.lengthOfMonth() // 이번 달 총 일 수
    val nextMonthDays = (7 - ((startDayOfWeek + monthDays) % 7)) % 7 // 마지막 주의 남은 빈칸

    return (0 until startDayOfWeek + monthDays + nextMonthDays)
        .chunked(7) { it.map { offset -> getDay(yearMonth, startDayOfWeek, offset) } }
}

private fun getDay(yearMonth: YearMonth, startDayOfWeek: Int, offset: Int): DayEntity {
    val date = yearMonth.atDay(1).minusDays(startDayOfWeek.toLong()).plusDays(offset.toLong())
    return DayEntity(day = date, isOtherMonth = YearMonth.from(date) != yearMonth)
}
