package com.sopt.core.util.calendar

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun String.toFormattedKoreanDate(): String {
    return try {
        val date = LocalDate.parse(this.substring(0, 10))
        val month = date.monthValue
        val day = date.dayOfMonth
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        "${month}월 ${day}일 ($dayOfWeek)"
    } catch (e: Exception) {
        this
    }
}
