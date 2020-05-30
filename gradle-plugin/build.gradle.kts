import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("kotlin-kapt")
    id("java-gradle-plugin")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("maven-publish")
}

gradlePlugin {
    (plugins) {
        register("no-inline-class-constructor") {
            id = "dev.yuku.no-inline-class-constructor"
            implementationClass = "dev.yuku.nicc.NiccGradlePlugin"
        }
    }
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

afterEvaluate {
    publishing {
        publications.create<MavenPublication>("no-inline-class-constructor") {
            artifact(tasks.getByName("shadowJar"))
            artifactId = rootProject.name
            pom {
                description.set("Static code analysis for Kotlin")
                name.set("no-inline-class-constructor")
//                url.set("https://arturbosch.github.io/detekt")
//                licenses {
//                    license {
//                        name.set("The Apache Software License, Version 2.0")
//                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                        distribution.set("repo")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("Artur Bosch")
//                        name.set("Artur Bosch")
//                        email.set("arturbosch@gmx.de")
//                    }
//                }
//                scm {
//                    url.set("https://github.com/arturbosch/detekt")
//                }
            }
        }
    }
}
