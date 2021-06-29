package com.github.belyakovleonid.core.presentation

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DATE_FORMATTER_PATTERN = "dd-MM"

fun LocalDate.toDateString(pattern: String = DATE_FORMATTER_PATTERN): String {
    return DateTimeFormatter.ofPattern(pattern).format(this)
}

fun getCurrentDate(): LocalDate = LocalDate.now()