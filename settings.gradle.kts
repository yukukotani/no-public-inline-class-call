pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "dev.yuku.no-inline-class-call") {
                useModule("dev.yuku:no-inline-class-call:${requested.version}")
            }
        }
    }
}

rootProject.name = "no-public-inline-class-call"
include("gradle-plugin")
include("kotlin-plugin")
include("example")