package dev.yuku.npicc

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CommandLineProcessor::class)
class NpiccCommandLineProcessor : CommandLineProcessor {
    override val pluginId = "no-inline-class-constructor"
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption(
            optionName = "enabled",
            valueDescription = "<true/false>",
            description = "whether plugin is enabled",
            required = false
        )
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            "enabled" -> configuration.put(NpiccConfigurationKeys.ENABLED, value.toBoolean())
            else -> error("unexpected option: ${option.optionName}")
        }
    }
}