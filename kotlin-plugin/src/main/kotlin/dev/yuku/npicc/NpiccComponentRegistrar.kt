package dev.yuku.npicc

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

@AutoService(ComponentRegistrar::class)
class NpiccComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        val severity = when(configuration[NpiccConfigurationKeys.SEVERITY]) {
            "ERROR", null -> CompilerMessageSeverity.ERROR
            "WARN" -> CompilerMessageSeverity.WARNING
            else -> throw IllegalArgumentException("severity must be ERROR or WARN")
        }
        val collector = configuration[CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY]
            ?: MessageCollector.NONE

        AnalysisHandlerExtension.registerExtension(
            project,
            NiccAnalysisHandlerExtension(collector, severity)
        )
    }
}