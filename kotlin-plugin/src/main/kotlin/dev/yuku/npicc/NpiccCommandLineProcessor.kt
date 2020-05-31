package dev.yuku.npicc

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CommandLineProcessor::class)
class NpiccCommandLineProcessor : CommandLineProcessor {
    override val pluginId = "no-public-inline-class-call"
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = "severity",
            valueDescription = "<error/warn>",
            description = "severity",
            required = false
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            "severity" -> configuration.put(NpiccConfigurationKeys.SEVERITY, value)
            else -> error("unexpected option: ${option.optionName}")
        }
    }
}