package com.myproject.gymphysique.core.common

import java.util.UUID

fun String.toUUID(): UUID =
    UUID.fromString(this)
fun ByteArray.toHexString(): String = joinToString(separator = " ") { byte -> "0x%02x".format(byte) }
