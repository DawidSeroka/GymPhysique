plugins {
    id("android.feature")
    id("android.library.compose")
    id("android.library.jacoco")
}

android {
    namespace = "com.myproject.gymphysique.feature.charts"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.timber)
}
