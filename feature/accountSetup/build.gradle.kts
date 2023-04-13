plugins {
    id("android.feature")
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.myproject.gymphysique.accountsetup"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.timber)
}