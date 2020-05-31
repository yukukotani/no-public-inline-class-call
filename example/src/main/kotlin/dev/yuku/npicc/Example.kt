package dev.yuku.npicc

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
    val valid = EmailAddress.fromString("example@gmail.com")
    val invalid = EmailAddress.fromString("invalid-email")
    val compileError = EmailAddress("invalid-email")
}