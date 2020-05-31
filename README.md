# Experimental: no-public-inline-class-call plugin

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/dev/yuku/no-public-inline-class-call/dev.yuku.no-public-inline-class-call.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=Gradle%20Plugin%20Portal)](https://plugins.gradle.org/plugin/dev.yuku.no-public-inline-class-call)

no-public-inline-class-call is a Gradle plugin (and Kotlin Compiler Plugin internally) to detect public constructor call for inline class.

## Use

This plugin forces you to call the inline class constructor within that class, which means you can validate inline class value with factory method.

## Example

```kotlin
inline class EmailAddress(val value: String) {
    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9.!#\$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$")

        fun fromString(str: String): EmailAddress {
            require(EMAIL_REGEX.matches(str)) { "Invalid email address: $str" }

            return EmailAddress(str)
        }
    }
}

fun main() {
    // passed validation
    val valid = EmailAddress.fromString("example@gmail.com")
    // blocked by validation
    val invalid = EmailAddress.fromString("invalid-email")
    // compile error, because of calling constructor directly
    val compileError = EmailAddress("invalid-email")
}
```

Whole example project is [here](https://github.com/Monchi/no-public-inline-class-call/tree/master/example).

## Installation

Kotlin DSL

```kotlin
plugins {
  id("dev.yuku.no-public-inline-class-call") version "0.1.0"
}
```

or Groovy

```groovy
plugins {
  id "dev.yuku.no-public-inline-class-call" version "0.1.0"
}
```

## Configuration

#### severity

Setting the severity to just warning instead of compile error.

```kotlin
`no-public-inline-class-call` {
   severity = dev.yuku.npicc.NpiccSeverity.WARN
}
```
