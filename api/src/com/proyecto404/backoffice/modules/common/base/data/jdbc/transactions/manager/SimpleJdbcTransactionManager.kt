package com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager

import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.JdbcTransaction

class SimpleJdbcTransactionManager(dataSource: DataSource): JdbcTransactionManager(dataSource) {
    override var activeTransaction: JdbcTransaction? = null
}
