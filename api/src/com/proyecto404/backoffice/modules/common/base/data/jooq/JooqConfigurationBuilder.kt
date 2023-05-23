package com.proyecto404.backoffice.modules.common.base.data.jooq

import com.nbottarini.asimov.environment.Env
import com.nbottarini.asimov.lang.DetailsExt
import com.proyecto404.backoffice.modules.common.base.data.jdbc.DataSource
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.HikariCPConnectionFactory
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.JdbcConnectionFactory
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.SimpleJdbcConnectionFactory
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcCredentialsFromEnvironmentFactory
import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcUrl
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager.JdbcTransactionManager
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager.SimpleJdbcTransactionManager
import com.proyecto404.backoffice.modules.common.base.data.jdbc.transactions.manager.ThreadLocalJdbcTransactionManager

class JooqConfigurationBuilder {
    private var credentials = JdbcCredentials(JdbcUrl("", "", 0, ""), "", "")
    private var logSql = false
    private var createConnectionFactory: () -> JdbcConnectionFactory = { HikariCPConnectionFactory(credentials) }
    private var createTransactionManagerFactory: (DataSource) -> JdbcTransactionManager = { ThreadLocalJdbcTransactionManager(it) }

    fun credentials(credentials: JdbcCredentials) = credentials.also { this.credentials = it }

    fun credentialsFromEnv(prefix: String = "DB"): JdbcCredentials {
        return JdbcCredentialsFromEnvironmentFactory().create(prefix).also { this.credentials = it }
    }

    fun logSql(value: Boolean = true) = value.also { logSql = it }

    fun logSqlFromEnv(name: String = "JOOQ_LOG_SQL") = (Env[name] == "1").also { logSql = it }

    fun simpleConnections() { createConnectionFactory = { SimpleJdbcConnectionFactory(credentials) } }

    fun pooledConnections(maxPoolSize: Int = 10) {
        this.createConnectionFactory = { HikariCPConnectionFactory(credentials, maxPoolSize) }
    }

    fun connectionFactory(factory: JdbcConnectionFactory) = apply {
        createConnectionFactory = { factory }
    }

    fun simpleTransactions() = apply { createTransactionManagerFactory = { SimpleJdbcTransactionManager(it) } }

    fun threadLocalTransactions() = apply { createTransactionManagerFactory = { ThreadLocalJdbcTransactionManager(it) } }

    fun build(): JooqConfiguration {
        val dataSource = DataSource(
            createConnectionFactory(),
            credentials,
            createTransactionManagerFactory
        )
        return JooqConfiguration(dataSource, logSql)
    }
}

fun jooqConfiguration(addDetails: DetailsExt<JooqConfigurationBuilder>): JooqConfiguration {
    return JooqConfigurationBuilder().also(addDetails).build()
}
