plugins {
    id("android.feature")
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.myproject.gymphysique.feature.measurements"
}

dependencies {
    implementation(project(":core:bluetooth"))
    implementation(project(":core:decoder"))
    implementation(project(":core:designsystem"))
    implementation(libs.timber)
    implementation(libs.kable.android.ble)
    implementation(libs.composePermissionHandler.core)
    implementation(libs.composePermissionHandler.utils)
}