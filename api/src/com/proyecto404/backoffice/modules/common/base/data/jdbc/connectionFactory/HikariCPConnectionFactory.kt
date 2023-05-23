package com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory

import com.proyecto404.backoffice.modules.common.base.data.jdbc.connectionFactory.credentials.JdbcCredentials
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.PrintWriter
import java.sql.Connection

class HikariCPConnectionFactory(private val credentials: JdbcCredentials, maxPoolSize: Int = 10):
    JdbcConnectionFactory {
    private val dataSource: HikariDataSource
    override val databaseDriver
        get() = credentials.url.driver

    init {
        val config = HikariConfig()
        config.maximumPoolSize = maxPoolSize
        config.jdbcUrl = credentials.url.toString()
        config.username = credentials.userName
        config.password = credentials.password
        dataSource = HikariDataSource(config)
    }

    override fun getConnection(): Connection = dataSource.connection

    override var logWriter: PrintWriter?
        get() = dataSource.logWriter
        set(value) { dataSource.logWriter = value }

    override var loginTimeoutSecs: Int
        get() = dataSource.loginTimeout
        set(value) { dataSource.loginTimeout = value }
}
