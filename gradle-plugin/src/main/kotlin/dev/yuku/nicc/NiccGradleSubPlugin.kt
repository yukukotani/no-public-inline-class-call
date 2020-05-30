package dev.yuku.nicc

import com.google.auto.service.AutoService
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinGradleSubplugin::class)
class NiccGradleSubPlugin : KotlinGradleSubplugin<AbstractCompile> {
    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        val extension = project.extensions.findByType(NiccGradleExtension::class.java)
            ?: NiccGradleExtension()
        return listOf(
            SubpluginOption(
                key = "enabled", value = extension.enabled.toString()
            )
        )
    }

    override fun getCompilerPluginId(): String {
        return "no-inline-class-constructor"
    }

    override fun getPluginArtifact(): SubpluginArtifact {
        return SubpluginArtifact(
            groupId = "dev.yuku",
            artifactId = "no-inline-class-constructor",
            version = "1.0-SNAPSHOT"
        )
    }

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
        return project.plugins.hasPlugin(NiccGradlePlugin::class.java)
    }

}