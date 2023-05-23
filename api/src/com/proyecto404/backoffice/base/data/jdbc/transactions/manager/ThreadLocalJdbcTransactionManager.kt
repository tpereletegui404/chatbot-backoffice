package com.proyecto404.backoffice.base.data.jdbc.transactions.manager

import com.proyecto404.backoffice.base.data.jdbc.DataSource
import com.proyecto404.backoffice.base.data.jdbc.transactions.JdbcTransaction

class ThreadLocalJdbcTransactionManager(dataSource: com.proyecto404.backoffice.base.data.jdbc.DataSource): JdbcTransactionManager(dataSource) {
    private var threadLocalActiveTransaction: ThreadLocal<JdbcTransaction?> = ThreadLocal()

    override var activeTransaction: JdbcTransaction?
        get() = threadLocalActiveTransaction.get()
        set(value) { threadLocalActiveTransaction.set(value) }
}
