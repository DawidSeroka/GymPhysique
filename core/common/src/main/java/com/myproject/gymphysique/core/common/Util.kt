package com.myproject.gymphysique.core.common

import java.util.UUID

fun String.toUUID(): UUID =
    UUID.fromString(this)