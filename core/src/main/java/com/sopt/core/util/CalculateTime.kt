package com.sopt.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class CalculateTime {
    fun extractDate(dateTime: String): String {
        return parseDateTime(dateTime).toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd"))
    }

    fun extractDayOfWeek(dateTime: String): String {
        return parseDateTime(dateTime).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }

    fun extractHour(dateTime: String): String {
        return "${parseDateTime(dateTime).hour}시"
    }

    fun extractFullDate(dateTime: String): String {
        val parsedDateTime = parseDateTime(dateTime)
        val year = parsedDateTime.year
        val month = parsedDateTime.monthValue
        val day = parsedDateTime.dayOfMonth
        val dayOfWeek = parsedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)

        return "${year}년 ${month}월 ${day}일 ${dayOfWeek}"
    }

    private fun parseDateTime(dateTime: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(dateTime, formatter)
    }
}