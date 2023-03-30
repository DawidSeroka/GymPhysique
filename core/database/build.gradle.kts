plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
    id("android.room")
    kotlin("kapt")
}

android {
    namespace = "com.myproject.gymphysique.core.database"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}