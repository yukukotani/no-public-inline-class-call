pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "dev.yuku.no-inline-class-constructor") {
                useModule("dev.yuku:no-inline-class-constructor:${requested.version}")
            }
        }
    }
}

rootProject.name = "no-inline-class-constructor"
include("gradle-plugin")
include("kotlin-plugin")
include("example")

//findProject(":kotlin-plugin")?.name = "kotlin-plugin"