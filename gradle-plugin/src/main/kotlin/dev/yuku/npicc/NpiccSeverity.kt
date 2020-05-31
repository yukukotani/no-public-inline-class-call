package dev.yuku.npicc

enum class NpiccSeverity {
    ERROR,
    WARN;

    companion object {
        fun fromString(value: String): NpiccSeverity {
            return valueOf(value.toUpperCase())
        }
    }
}