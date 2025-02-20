package com.sopt.core.util.calendar

import java.time.LocalDate
import java.time.YearMonth

class Calendar{
    fun getFirstDayOfWeek(year: Int, month: Int): Int =
        LocalDate.of(year, month, 1).dayOfWeek.value % 7

    fun getDaysInMonth(year: Int, month: Int): Int =
        YearMonth.of(year, month).lengthOfMonth()

    fun dateFormat(year: Int, month: Int, day: Int): String =
        "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"

    fun generateDateRange(startDate: String, endDate: String): List<String> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        return (0..end.toEpochDay() - start.toEpochDay())
            .map { start.plusDays(it).toString() }
    }

    fun manageMonth(year: Int, month: Int, isNext: Boolean): Pair<Int, Int> {
        return if (isNext) {
            if (month == 12) Pair(year + 1, 1) else Pair(year, month + 1)
        } else {
            if (month == 1) Pair(year - 1, 12) else Pair(year, month - 1)
        }
    }

    fun singleDateSelection(
        dateValue: String,
        selectedDates: List<String>,
        onShowSnackBar: () -> Unit
    ): List<String> {
        return if (dateValue in selectedDates) {
            selectedDates - dateValue
        } else if (selectedDates.size < 7) {
            selectedDates + dateValue
        } else {
            onShowSnackBar()
            selectedDates
        }
    }

    fun periodDateSelection(
        dateValue: String,
        startDate: String,
        endDate: String,
        onShowSnackBar: () -> Unit,
        selectedPeriod: (List<String>) -> Unit
    ): Pair<String, String> {
        var tempStartDate = startDate
        var tempEndDate = endDate

        if (tempStartDate.isNotEmpty() && tempEndDate.isNotEmpty() || dateValue == tempStartDate || dateValue == tempEndDate) {
            tempStartDate = ""
            tempEndDate = ""
            selectedPeriod(emptyList())
        } else if (tempStartDate.isEmpty()) {
            tempStartDate = dateValue
        } else {
            val valueLocalDate = LocalDate.parse(dateValue)
            val startLocalDate = LocalDate.parse(tempStartDate)
            if (valueLocalDate.isBefore(startLocalDate)) {
                if (valueLocalDate.plusDays(6).isBefore(startLocalDate)) {
                    onShowSnackBar()
                } else {
                    tempEndDate = tempStartDate
                    tempStartDate = dateValue
                }
            } else {
                if (startLocalDate.plusDays(6).isBefore(valueLocalDate)) {
                    onShowSnackBar()
                } else {
                    tempEndDate = dateValue
                }
            }
        }
        return tempStartDate to tempEndDate
    }
}
