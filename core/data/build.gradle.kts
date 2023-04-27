@file:Suppress("UnstableApiUsage")

plugins {
    id("android.library")
    id("android.library.jacoco")
    id("kotlinx-serialization")
    id("android.hilt")
    kotlin("kapt")
}

android {
    namespace = "com.myproject.gymphysique.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
