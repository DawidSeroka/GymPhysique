package com.myproject.gymphysique.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getCurrentDate(): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        .withZone(ZoneId.systemDefault())
    return formatter.format(Instant.now())
}