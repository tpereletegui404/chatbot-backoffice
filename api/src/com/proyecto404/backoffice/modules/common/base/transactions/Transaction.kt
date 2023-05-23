package com.proyecto404.backoffice.modules.common.base.transactions

interface Transaction: AutoCloseable {
    val isClosed: Boolean
    fun commit()
    fun rollback()
}
