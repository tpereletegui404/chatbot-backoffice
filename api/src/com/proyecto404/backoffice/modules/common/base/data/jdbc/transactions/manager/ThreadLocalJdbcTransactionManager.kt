package com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager

import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.JdbcTransaction

class ThreadLocalJdbcTransactionManager(dataSource: DataSource): JdbcTransactionManager(dataSource) {
    private var threadLocalActiveTransaction: ThreadLocal<JdbcTransaction?> = ThreadLocal()

    override var activeTransaction: JdbcTransaction?
        get() = threadLocalActiveTransaction.get()
        set(value) { threadLocalActiveTransaction.set(value) }
}
