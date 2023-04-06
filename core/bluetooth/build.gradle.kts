plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.myproject.gymphysique.core.bluetooth"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    api(libs.kable.android.ble)
    implementation(libs.timber)
}