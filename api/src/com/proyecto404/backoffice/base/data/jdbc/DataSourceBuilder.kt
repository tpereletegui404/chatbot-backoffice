package com.proyecto404.backoffice.base.data.jdbc

import com.nbottarini.asimov.lang.DetailsExt
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.HikariCPConnectionFactory
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.JdbcConnectionFactory
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.SimpleJdbcConnectionFactory
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.credentials.JdbcCredentialsFromEnvironmentFactory
import com.proyecto404.backoffice.base.data.jdbc.connectionFactory.credentials.JdbcUrl
import com.proyecto404.backoffice.base.data.jdbc.transactions.manager.JdbcTransactionManager
import com.proyecto404.backoffice.base.data.jdbc.transactions.manager.SimpleJdbcTransactionManager
import com.proyecto404.backoffice.base.data.jdbc.transactions.manager.ThreadLocalJdbcTransactionManager

class DataSourceBuilder {
    private var credentials = JdbcCredentials(JdbcUrl("", "", 0, ""), "", "")
    private var createConnectionFactory: () -> JdbcConnectionFactory = { HikariCPConnectionFactory(credentials) }
    private var createTransactionManagerFactory: (com.proyecto404.backoffice.base.data.jdbc.DataSource) -> JdbcTransactionManager = { ThreadLocalJdbcTransactionManager(it) }

    fun dbCredentials(credentials: JdbcCredentials) = credentials.also { this.credentials = it }

    fun dbCredentialsFromEnv(prefix: String = "DB"): JdbcCredentials {
        return JdbcCredentialsFromEnvironmentFactory().create(prefix).also { this.credentials = it }
    }

    fun simpleDbConnections() { createConnectionFactory = { SimpleJdbcConnectionFactory(credentials) } }

    fun pooledDbConnections(maxPoolSize: Int = 10) {
        this.createConnectionFactory = { HikariCPConnectionFactory(credentials, maxPoolSize) }
    }

    fun simpleTransactions() = apply { createTransactionManagerFactory = { SimpleJdbcTransactionManager(it) } }

    fun threadLocalTransactions() = apply { createTransactionManagerFactory = { ThreadLocalJdbcTransactionManager(it) } }

    fun build() = com.proyecto404.backoffice.base.data.jdbc.DataSource(
        createConnectionFactory(),
        credentials,
        createTransactionManagerFactory
    )
}

fun dataSource(addDetails: DetailsExt<com.proyecto404.backoffice.base.data.jdbc.DataSourceBuilder>) = com.proyecto404.backoffice.base.data.jdbc.DataSourceBuilder()
    .apply(addDetails).build()
