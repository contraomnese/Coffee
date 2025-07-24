pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Coffee"
include(":app")
include(":core:ui")
include(":core:design")
include(":core:presentation")
include(":data")
include(":domain")
include(":features:register")
include(":features:login")
include(":features:menu")
include(":features:locations")
include(":features:order")
include(":features:map")
