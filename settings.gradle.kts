pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "GymPhysique"
include(":app")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:common")
include(":core:model")
include(":core:ui")
include(":core:testing")
include(":feature:settings")
include(":feature:measure")
include(":feature:devices")
include(":core:designsystem")
include(":feature:charts")
include(":core:bluetooth")
include(":core:domain")
include(":core:decoder")
