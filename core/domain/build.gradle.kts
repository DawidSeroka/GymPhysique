plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.myproject.gymphysqiue.core.domain"
}

dependencies {
    implementation(project(":core:decoder"))
    implementation(project(":core:bluetooth"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.timber)
}
