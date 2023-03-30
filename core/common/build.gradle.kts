plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
}

android {
    namespace = "com.myproject.gymphysique.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}