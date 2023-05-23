package com.proyecto404.backoffice.base.lang

fun String.splitWithoutPrefix(value: String) = this.removePrefix(value).split(value)

fun String.ifLengthLessThan(size: Int, block: () -> Unit) {
    if (this.length < size) block()
}

fun String.ifLengthNot(size: Int, block: () -> Unit) {
    if (this.length != size) block()
}
