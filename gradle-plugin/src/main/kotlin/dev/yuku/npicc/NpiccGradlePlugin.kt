package dev.yuku.npicc

import org.gradle.api.Plugin
import org.gradle.api.Project

class NpiccGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create(
            "no-public-inline-class-call",
            NpiccGradleExtension::class.java
        )
    }

}