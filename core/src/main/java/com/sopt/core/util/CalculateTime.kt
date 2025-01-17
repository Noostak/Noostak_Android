package com.sopt.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class CalculateTime {
    fun extractFullDateWithSlash(dateTime: String): String {
        return parseDateTime(dateTime).toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd"))
    }

    fun extractDateWithSlash(dateTime: String): String {
        return parseDateTime(dateTime).toLocalDate().format(DateTimeFormatter.ofPattern("M/d"))
    }

    fun extractFullDateWithKorean(dateTime: String): String {
        return parseDateTime(dateTime).toLocalDate().format(DateTimeFormatter.ofPattern("MM월 dd일"))
    }

    fun extractDateWithKorean(dateTime: String): String {
        return parseDateTime(dateTime).toLocalDate().format(DateTimeFormatter.ofPattern("M월 d일"))
    }

    fun extractDayOfWeek(dateTime: String): String {
        return parseDateTime(dateTime).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }

    fun extractDayOfWeekWithBraces(dateTime: String): String {
        return "(${parseDateTime(dateTime).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)})"
    }

    fun extractHourWithKorean(dateTime: String): String {
        return "${parseDateTime(dateTime).hour}시"
    }

    fun extractHourWithZero(dateTime: String): String {
        return "${parseDateTime(dateTime).hour}:00"
    }

    private fun parseDateTime(dateTime: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(dateTime, formatter)
    }
}
