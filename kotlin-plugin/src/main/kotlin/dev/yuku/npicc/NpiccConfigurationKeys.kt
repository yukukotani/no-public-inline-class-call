package dev.yuku.npicc

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object NpiccConfigurationKeys {
    val ENABLED: CompilerConfigurationKey<Boolean> = CompilerConfigurationKey.create("enabled")
}