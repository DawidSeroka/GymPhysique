package com.myproject.gymphysique.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class Date {
    fun formatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("yyyy-MM")
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
