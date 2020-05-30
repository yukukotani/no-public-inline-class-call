buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    id("dev.yuku.no-public-inline-class-call") version "1.0-SNAPSHOT"
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
