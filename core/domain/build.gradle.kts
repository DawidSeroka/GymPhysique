import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
    kotlin("kapt")
}

android {
    namespace = "com.myproject.gymphysique.core.domain"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    implementation(libs.hilt.android)
}