package com.github.belyakovleonid.core.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

private const val DATE_FORMATTER_PATTERN = "dd-MM"

fun getDateString(date: LocalDate): String {
    return DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN).format(date.toJavaLocalDate())
}