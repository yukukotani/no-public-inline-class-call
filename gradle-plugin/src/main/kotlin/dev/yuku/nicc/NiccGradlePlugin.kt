package dev.yuku.nicc

import org.gradle.api.Plugin
import org.gradle.api.Project

class NiccGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create(
            "no-inline-class-constructor",
            NiccGradleExtension::class.java
        )
    }

}