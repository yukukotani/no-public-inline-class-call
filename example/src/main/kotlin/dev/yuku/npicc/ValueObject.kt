package dev.yuku.npicc

import dev.yuku.npicc.test.TestObject

inline class ValueObject(val value: Long) {
    companion object {
        fun test() {
            ValueObject(2)
        }
    }
}

fun main() {
    val a = ValueObject(1)
    val test = TestObject("aa")
}