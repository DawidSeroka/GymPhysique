buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
    apply(plugin = "io.gitlab.arturbosch.detekt")

    repositories {
        mavenCentral()
    }
}

allprojects {
    detekt {
        config = files("$rootDir/config/detekt/detekt.yml")
    }
}
