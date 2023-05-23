package com.proyecto404.backoffice.core.common.domain

import com.proyecto404.backoffice.base.testing.TestExamples
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateExamples {
    val dates = TestExamples(LocalDate.of(2020, 1, 1), LocalDate.of(2021, 2, 3), LocalDate.of(2021, 5, 4), LocalDate.of(2020, 10, 15))
    val dateTimes = TestExamples(LocalDateTime.of(2020, 1, 1, 7, 5), LocalDateTime.of(2020, 3, 4, 8, 45), LocalDateTime.of(2020, 12, 23, 3, 25))
    val now = LocalDateTime.of(2020, 7, 20, 10, 20, 30)

    fun date(stringDate: String) = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("d/M/yyyy"))

    fun time(stringTime: String) = LocalTime.parse(stringTime)

    fun dateTime(stringDate: String): LocalDateTime {
        if (stringDate.length <= 10) return date(stringDate).atStartOfDay()
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("d/M/yyyy H:m")).withSecond(0).withNano(0)
    }
}