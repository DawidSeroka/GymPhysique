plugins {
    id("android.feature")
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.myproject.gymphysique.accountsetup"

    packagingOptions {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.timber)
}
