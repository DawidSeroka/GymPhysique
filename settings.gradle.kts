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
    }
}
rootProject.name = "GymPhysique"
include(":app")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:common")
include(":core:model")
include(":core:ui")
include(":core:testing")
include(":feature:settings")
include(":feature:measurements")
include(":feature:devices")
