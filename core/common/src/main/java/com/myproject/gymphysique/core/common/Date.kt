package com.myproject.gymphysique.core.common

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class Date {
    fun formatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(FORMAT_PATTERN_YEAR_MONTH)
            .withZone(ZoneId.systemDefault())
    }
}
fun getCurrentDate(): String {
    val formatter = com.myproject.gymphysique.core.common.Date().formatter()
    return formatter.format(Instant.now())
}

fun Date.toMonthAndYear(): String {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = com.myproject.gymphysique.core.common.Date().formatter()
    return formatter.format(localDate)
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat(FORMAT_PATTERN_YEAR_MONTH)
    return dateFormat.parse(this) ?: Date()
}

const val FORMAT_PATTERN_YEAR_MONTH = "yyyy-MM"
