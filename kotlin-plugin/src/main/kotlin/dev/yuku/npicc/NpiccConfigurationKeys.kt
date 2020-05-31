package dev.yuku.npicc

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object NpiccConfigurationKeys {
    val SEVERITY: CompilerConfigurationKey<String> = CompilerConfigurationKey.create("severity")
}