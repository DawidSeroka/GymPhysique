plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.myproject.gymphysique.core.decoder"
}

dependencies {
    implementation(project(":core:common"))
}