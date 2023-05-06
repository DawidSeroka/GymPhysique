package com.myproject.gymphysique.core.model

enum class Gender(val fullName: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

fun String.toGender(): Gender {
    return when (this.lowercase()) {
        Gender.MALE.fullName.lowercase() -> Gender.MALE
        Gender.FEMALE.fullName.lowercase() -> Gender.FEMALE
        else -> Gender.OTHER
    }
}
