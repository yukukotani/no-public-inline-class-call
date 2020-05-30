import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("kotlin-kapt")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.12.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))

    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")

    compileOnly(project(":kotlin-plugin"))
}

tasks {
    named<ShadowJar>("shadowJar") {
        configurations = listOf(project.configurations.compileOnly.get())
        archiveClassifier.set(null as String?)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

gradlePlugin {
    (plugins) {
        register("no-public-inline-class-call") {
            id = "dev.yuku.no-public-inline-class-call"
            displayName = "no-public-inline-class-call-plugin"
            description = "Prohibiting public inline class constructor call."
            implementationClass = "dev.yuku.npicc.NpiccGradlePlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/Monchi/no-public-inline-class-call"
    vcsUrl = "https://github.com/Monchi/no-public-inline-class-call"
    tags = listOf("kotlin", "inline-class", "kotlin-compiler-plugin")
}

afterEvaluate {
    publishing {
        publications.create<MavenPublication>("no-public-inline-class-call") {
            artifact(tasks.getByName("shadowJar"))
            artifactId = rootProject.name
            pom {
                description.set("Prohibiting public inline class constructor call.")
                name.set("no-public-inline-class-call")
            }
        }
    }
}
