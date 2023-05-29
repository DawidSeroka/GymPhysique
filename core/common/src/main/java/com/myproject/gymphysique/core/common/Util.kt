package com.myproject.gymphysique.core.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit

fun String.toUUID(): UUID =
    UUID.fromString(this)

fun ByteArray.toHexString(): String =
    joinToString(separator = " ") { byte -> "0x%02x".format(byte) }

@Suppress("MagicNumber")
fun signedByteToInt(b: Byte): Int = b.toInt() and 255

@Suppress("MagicNumber")
fun signedBytesToInt(b0: Byte, b1: Byte): Int =
    signedByteToInt(b0) + (signedByteToInt(b1) shl 8)

@SuppressLint("SimpleDateFormat")
@Suppress("MagicNumber")
fun byteArrayToDate(byteArray: ByteArray): Date {
    val byteList = byteArray.map { it.toUByte().toByte() }
    val second = byteList[6].toInt()
    val minute = byteList[5].toInt()
    val hour = byteList[4].toInt()
    val day = byteList[3].toInt()
    val month = byteList[2].toInt()
    val year = signedBytesToInt(byteList.first(), byteList[1])

    val dateFormat = SimpleDateFormat("dd, MM, yyyy, HH:mm:ss")
    return dateFormat.parse("$day, $month, $year, $hour:$minute:$second")!!
}

fun dateToTimestamp(byteArray: ByteArray): Long {
    val date = byteArrayToDate(byteArray)
    return date.time
}

fun Long.toMillis(): Long =
    TimeUnit.SECONDS.toMillis(this)
