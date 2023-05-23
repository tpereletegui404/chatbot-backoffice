package com.proyecto404.backoffice.base.data.jdbc.transactions

import com.proyecto404.backoffice.base.transactions.Transaction
import java.sql.Connection
import java.sql.Savepoint
import java.util.*

class JdbcTransaction(val connection: Connection, private val onClose: () -> Unit): Transaction {
    private var savepoints = ArrayDeque<Savepoint>()
    override var isClosed = false
        private set

    init {
        connection.autoCommit = false
    }

    fun beginNested() {
        val savepoint = connection.setSavepoint()
        savepoints.push(savepoint)
    }

    override fun commit() {
        if (isClosed) return

        if (hasNested()) {
            commitNested()
            return
        }

        connection.commit()
        connection.autoCommit = true
        setClosed()
    }

    private fun commitNested() {
        val savepoint = savepoints.pop()
        connection.releaseSavepoint(savepoint)
    }

    private fun hasNested() = savepoints.isNotEmpty()

    override fun rollback() {
        if (isClosed) return

        if (hasNested()) {
            rollbackNested()
            return
        }

        connection.rollback()
        connection.autoCommit = true
        setClosed()
    }

    private fun rollbackNested() {
        val savepoint = savepoints.pop()
        connection.rollback(savepoint)
    }

    private fun setClosed() {
        isClosed = true
        onClose()
    }

    override fun close() {
        if (isClosed) return
        rollback()
    }
}
