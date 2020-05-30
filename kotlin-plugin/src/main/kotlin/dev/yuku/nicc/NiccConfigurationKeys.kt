package dev.yuku.nicc

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object NiccConfigurationKeys {
    val ENABLED: CompilerConfigurationKey<Boolean> = CompilerConfigurationKey.create("enabled")
}