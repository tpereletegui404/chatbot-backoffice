package com.proyecto404.backoffice.base.transactions

interface Transaction: AutoCloseable {
    val isClosed: Boolean
    fun commit()
    fun rollback()
}
