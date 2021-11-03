enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.version.toml"))
        }
    }
}

rootProject.name = "Summary"
include(":app")
include(":shared:weather")
include(":shared:city")
include(":libraries:network")
include(":libraries:storage")
include(":libraries:utils")