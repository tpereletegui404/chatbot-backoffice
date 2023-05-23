package com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager

import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.JdbcTransaction
import com.proyecto404.backoffice.modules.common.base.transactions.Transaction
import com.proyecto404.backoffice.modules.common.base.transactions.TransactionManager
import java.sql.Connection

abstract class JdbcTransactionManager(private val dataSource: DataSource):
    TransactionManager {
    protected abstract var activeTransaction: JdbcTransaction?

    val activeConnection: Connection?
        get() = activeTransaction?.connection

    override fun beginTransaction(): Transaction {
        if (hasActiveTransaction()) {
            activeTransaction!!.beginNested()
        } else {
            activeTransaction = createTransaction()
        }
        return activeTransaction!!
    }

    private fun createTransaction(): JdbcTransaction {
        return JdbcTransaction(dataSource.acquire(), ::onClose)
    }

    fun hasActiveTransaction() = activeTransaction != null

    private fun onClose() {
        val connection = activeConnection!!
        endTransaction()
        dataSource.release(connection)
    }

    private fun endTransaction() {
        activeTransaction = null
    }
}
