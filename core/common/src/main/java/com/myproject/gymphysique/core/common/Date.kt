package com.myproject.gymphysique.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DateTimeFormatter {
    fun formatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("yyyy-MM")
            .withZone(ZoneId.systemDefault())
    }
}
fun getCurrentDate(): String {
    val formatter = DateTimeFormatter().formatter()
    return formatter.format(Instant.now())
}

fun Date.toMonthAndYear(): String {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter().formatter()
    return formatter.format(localDate)
}
