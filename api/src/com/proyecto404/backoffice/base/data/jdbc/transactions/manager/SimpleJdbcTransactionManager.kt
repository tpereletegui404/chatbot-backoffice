package com.proyecto404.backoffice.base.data.jdbc.transactions.manager

import com.proyecto404.backoffice.base.data.jdbc.DataSource
import com.proyecto404.backoffice.base.data.jdbc.transactions.JdbcTransaction

class SimpleJdbcTransactionManager(dataSource: com.proyecto404.backoffice.base.data.jdbc.DataSource): JdbcTransactionManager(dataSource) {
    override var activeTransaction: JdbcTransaction? = null
}
