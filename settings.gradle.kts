pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
//        maven { url = uri("https://kitsu.io/api/edge") }
    }
}

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
//        maven { url = uri("https://kitsu.io/api/edge") }
    }
}


rootProject.name = "SignUp"
enableFeaturePreview("VERSION_CATALOGS")
include(":app", ":data", ":domain")