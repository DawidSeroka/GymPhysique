plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
    id("android.room")
    kotlin("kapt")
}

android {
    namespace = "com.myproject.gymphysique.core.database"

    defaultConfig {
        testInstrumentationRunner = "com.myproject.gymphysique.core.testing.GPTestRunner"
    }
    packagingOptions {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}
