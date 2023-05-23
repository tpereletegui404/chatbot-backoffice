package com.proyecto404.backoffice.modules.common.base.data.jooq

import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.transactions.Transaction
import com.proyecto404.backoffice.modules.common.base.transactions.transactional
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider

class JooqConfiguration(val dataSource: DataSource, logSql: Boolean = false): DefaultConfiguration() {
    val transactionManager get() = dataSource.transactionManager

    init {
        System.getProperties().setProperty("org.jooq.no-logo", "true")
        System.getProperties().setProperty("org.jooq.no-tips", "true")
        setDataSource(dataSource)
        setSQLDialect(createSQLDialectFromDriver(dataSource.databaseDriver))
        if (logSql) set(DefaultExecuteListenerProvider(SQLLogger()))
    }

    fun transactional(runnable: (Transaction) -> Unit) {
        transactionManager.transactional(runnable)
    }
}
